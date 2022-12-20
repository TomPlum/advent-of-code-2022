package io.github.tomplum.aoc.simluator.tower

import io.github.tomplum.libs.math.point.Point2D

class PyroclasticFlowSimulator(data: String) {

    private val flow = PyroclasticFlow(data)

    fun simulate(quantity: Long): Long {
        var currentRock = flow.getNextRock()
        var count = 0
        var rocks = 0L
        var x = 3 // Starts 2 units in (left wall == x=1)
        var y = 4 // Starts 3 units above the floor (floor == y=0)

        var extrapolatedHeight = 0L
        val heightsWhenCycling = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()

        while(rocks < quantity) {

            if (rocks > 1000) {
                val cacheKey = Pair(flow.rockIndex, flow.jetIndex)

                if (cacheKey in heightsWhenCycling) {
                    val (previousRocks, previousHeight) = heightsWhenCycling[cacheKey]!!
                    val period = rocks - previousRocks
                    if (period > 0 && rocks % period == quantity % period) {
                        val cycleHeight = flow.highestRockPosition - previousHeight
                        val remainingFullCycles = (quantity - rocks) / period
                        val extrapolatedRocks = remainingFullCycles * period
                        extrapolatedHeight = cycleHeight * remainingFullCycles
                        rocks += extrapolatedRocks
                    }
                } else {
                    heightsWhenCycling[cacheKey] = rocks.toInt() to flow.highestRockPosition
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
}