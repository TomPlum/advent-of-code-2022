package io.github.tomplum.aoc.simluator.tower

import io.github.tomplum.libs.math.point.Point2D

class PyroclasticFlowSimulator(data: String) {

    private val flow = PyroclasticFlow(data)

    fun simulate(): Int {
        var currentRock = flow.getNextRock()
        var count = 0
        var rocks = 0
        var x = 3 // Starts 2 units in (left wall == x=1)
        var y = 4 // Starts 3 units above the floor (floor == y=0)

        while(rocks < 2022) {
            //AdventLogger.debug("($x, $y)")
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
                    //AdventLogger.debug("Shifting rock $direction")
                } else {
                    //AdventLogger.debug("Can't shift $direction, nothing happens")
                }
            } else {
                val yNew = y - 1
                val newRockPositions = currentRock.positions(Point2D(x, yNew))
                val rockWillHitRestingPoint = yNew == 0 || flow.hasAnyRocksResting(newRockPositions)
                if (rockWillHitRestingPoint) {
                    flow.addRestingRock(currentRock.positions(Point2D(x, y)))
                    currentRock = flow.getNextRock()
                    x = 3
                    y = flow.getHighestRockPosition() + currentRock.height() + 3
                    rocks += 1
                    //AdventLogger.debug(flow)
                } else {
                    y = yNew
                    //AdventLogger.debug("Rock falls down")
                }
            }

            count++
        }

        return flow.getHighestRockPosition()
    }
}