package io.github.tomplum.aoc.simluator.tower

import io.github.tomplum.libs.math.point.Point2D

class PyroclasticFlowSimulator(data: String) {

    private val flow = PyroclasticFlow(data)

    private var count = 0
    private var rocks = 0L

    private var x = 3
    private var y = 4
    private var extrapolatedHeight = 0L

    private var currentRock = flow.getNextRock()

    fun simulate(quantity: Long): Long {


        val states = mutableMapOf<StateKey, StateValue>()

        while(rocks < quantity) {

            if (rocks > 1000) {
                val key = StateKey(flow.rockIndex, flow.jetIndex)

                if (key in states) {
                    val (extrapolatedRocks, extrapolatedHeight) = states[key]!!.extrapolateFlowHeight(quantity)
                    rocks += extrapolatedRocks
                    this.extrapolatedHeight = extrapolatedHeight
                } else {
                    states[key] = StateValue(rocks.toInt(), flow.highestRockPosition)
                }
            }

            val isInfluencedByJetStream = count % 2 == 0

            if (isInfluencedByJetStream) {
                pushRockViaJetStream()
            } else {
                letRockFall()
            }

            count++
        }

        return flow.highestRockPosition.toLong() + extrapolatedHeight
    }

    private fun pushRockViaJetStream() {
        val direction = flow.getNextJetPatternDirection()
        val xLeftNew = Point2D(x, y).shift(direction).x
        val newRockPositions = currentRock.positions(Point2D(xLeftNew, y))

        val xBoundary = (1..7)
        val xRightNew = currentRock.xRightMost(xLeftNew)

        val isWithinBounds = xLeftNew in xBoundary && xRightNew in xBoundary
        val isShiftingIntoRock = flow.hasAnyRocksResting(newRockPositions)

        if (isWithinBounds && !isShiftingIntoRock) {
            x = xLeftNew
        }
    }

    private fun letRockFall(): Rock {
        val yNew = y - 1
        val newRockPositions = currentRock.positions(Point2D(x, yNew)).reversed()

        val isComingToRest = flow.hasAnyRocksResting(newRockPositions) || newRockPositions.any { pos -> pos.y == 0 }
        if (isComingToRest) {
            flow.addRestingRock(currentRock.positions(Point2D(x, y)))
            currentRock = flow.getNextRock()
            x = 3
            y = flow.highestRockPosition + currentRock.height() + 3
            rocks += 1
        } else {
            y = yNew
        }
        return currentRock
    }

    data class StateKey(val rockTypeIndex: Int, val jetStreamIndex: Int)

    inner class StateValue(private val previousRockCount: Int, private val previousFlowHeight: Int) {
        fun extrapolateFlowHeight(rockQuantity: Long): Pair<Long, Long> {
            val period = rocks - previousRockCount
            if (period > 0 && rocks % period == rockQuantity % period) {
                val cycleHeight = flow.highestRockPosition - previousFlowHeight
                val remainingFullCycles = (rockQuantity - rocks) / period
                val extrapolatedRocks = remainingFullCycles * period
                val extrapolatedHeight = cycleHeight * remainingFullCycles
                return extrapolatedRocks to extrapolatedHeight
            }

            return 0L to 0L
        }
    }
}