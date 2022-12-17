package io.github.tomplum.aoc.simluator.tower

import io.github.tomplum.libs.logging.AdventLogger
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.point.Point2D

class PyroclasticFlowSimulator(data: String) {

    private val flow = PyroclasticFlow(data)

    fun simulate(): Int {
        var currentRock = flow.getNextRock()
        var count = 0
        var rocks = 1
        var x = 3 // Starts 2 units in (left wall == x=1)
        var y = 4 // Starts 3 units above the floor (floor == y=0)

        while(rocks < 2022) {
            AdventLogger.debug("($x, $y)")
            val isInfluencedByJetStream = count % 2 == 0
            if (isInfluencedByJetStream) {
                val direction = flow.getNextJetPatternDirection()
                val xLeftNew = Point2D(x, y).shift(direction).x
                val xRightNew = getRightMostX(xLeftNew, currentRock)
                val towerRange = (1..7)
                if (xLeftNew in towerRange && xRightNew in towerRange) {
                    x = xLeftNew
                    AdventLogger.debug("Shifting rock $direction")
                } else {
                    AdventLogger.debug("Can't shift $direction, nothing happens")
                }
            } else {
                val yNew = y - 1
                val rockWillHitRestingPoint = yNew == 0 || flow.hasRockRestingAt(Point2D(x, yNew))
                if (rockWillHitRestingPoint) {
                    flow.addRestingRock(getRockPositions(currentRock, Point2D(x, y)))
                    currentRock = flow.getNextRock()
                    x = 2
                    y = flow.getHighestRockPosition() + 3
                    rocks += 1
                    AdventLogger.debug(flow)
                } else {
                    y = yNew
                    AdventLogger.debug("Rock falls down")
                }
            }

            count++
        }

        return flow.getHighestRockPosition()
    }

    private fun getRockPositions(type: RockType, start: Point2D) = when(type) {
        RockType.STRAIGHT_H -> (start.x..start.x + 3).map { x -> Point2D(x, start.y) }
        RockType.PLUS -> {
            val top = start.shift(Direction.RIGHT)
            val left = start.shift(Direction.DOWN)
            val middle = left.shift(Direction.RIGHT)
            val right = middle.shift(Direction.RIGHT)
            val bottom = middle.shift(Direction.DOWN)
            listOf(top, left, middle, right, bottom)
        }
        RockType.L -> {
            val topRight = start.shift(Direction.RIGHT, 2)
            val rightSide = (topRight.y..topRight.y - 3).map { y -> Point2D(topRight.x , y) }
            val bottomMiddle = rightSide.last().shift(Direction.LEFT)
            val bottomLeft = bottomMiddle.shift(Direction.LEFT)
            listOf(bottomLeft, bottomMiddle) + rightSide
        }
        RockType.STRAIGHT_V -> (start.y..start.y + 3).map { y -> Point2D(start.x, y) }
        RockType.SQUARE -> {
            val topRight = start.shift(Direction.RIGHT)
            val bottomLeft = start.shift(Direction.DOWN)
            val bottomRight = bottomLeft.shift(Direction.RIGHT)
            listOf(start, topRight, bottomLeft, bottomRight)
        }
    }

    private fun getRightMostX(xLeft: Int, type: RockType) = when(type) {
        RockType.STRAIGHT_H -> xLeft + 3
        RockType.PLUS -> xLeft + 2
        RockType.L -> xLeft + 2
        RockType.STRAIGHT_V -> xLeft
        RockType.SQUARE -> xLeft + 1
    }
}