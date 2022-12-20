package io.github.tomplum.aoc.simluator.tower

import io.github.tomplum.libs.math.point.Point2D

class PyroclasticFlowSimulator(data: String) {

    private val flow = PyroclasticFlow(data)

    fun simulate(quantity: Long): Long {
        var count = 0
        var rocks = 0L

        var x = 3
        var y = 4

        var currentRock = flow.getNextRock()

        var extrapolatedHeight = 0L
        val states = mutableMapOf<StateKey, StateValue>()

        while(rocks < quantity) {

            if (rocks > 1000) {
                val stateKey = StateKey(flow.rockIndex, flow.jetIndex)

                if (stateKey in states) {
                    val (previousRocks, previousHeight) = states[stateKey]!!
                    val period = rocks - previousRocks
                    if (period > 0 && rocks % period == quantity % period) {
                        val cycleHeight = flow.highestRockPosition - previousHeight
                        val remainingFullCycles = (quantity - rocks) / period
                        val extrapolatedRocks = remainingFullCycles * period
                        extrapolatedHeight = cycleHeight * remainingFullCycles
                        rocks += extrapolatedRocks
                    }
                } else {
                    states[stateKey] = StateValue(rocks.toInt(), flow.highestRockPosition)
                }
            }

            val isInfluencedByJetStream = count % 2 == 0
            if (isInfluencedByJetStream) {
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
            } else {
                val yNew = y - 1
                val newRockPositions = currentRock.positions(Point2D(x, yNew)).reversed()

                val rockWillHitRestingPoint = flow.hasAnyRocksResting(newRockPositions) || newRockPositions.any { pos -> pos.y == 0 }
                if (rockWillHitRestingPoint) {
                    flow.addRestingRock(currentRock.positions(Point2D(x, y)))
                    currentRock = flow.getNextRock()
                    x = 3
                    y = flow.highestRockPosition + currentRock.height() + 3
                    rocks += 1
                } else {
                    y = yNew
                }
            }

            count++
        }

        return flow.highestRockPosition.toLong() + extrapolatedHeight
    }

    data class StateKey(val rockTypeIndex: Int, val jetStreamIndex: Int)

    data class StateValue(val previousRockCount: Int, val previousFlowHeight: Int)
}