package io.github.tomplum.aoc.simluator.tower.rocks

import io.github.tomplum.aoc.simluator.tower.Rock
import io.github.tomplum.aoc.simluator.tower.RockType
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.point.Point2D

class AngleRock : Rock {

    override val type: RockType
        get() = RockType.ANGLE

    override fun positions(start: Point2D): List<Point2D> {
        val topRight = start.shift(Direction.RIGHT, 2)
        val rightSide = (topRight.y downTo topRight.y - 2).map { y -> Point2D(topRight.x , y) }
        val bottomMiddle = rightSide.last().shift(Direction.LEFT)
        val bottomLeft = bottomMiddle.shift(Direction.LEFT)
        return listOf(bottomLeft, bottomMiddle) + rightSide
    }

    override fun xRightMost(xLeftMost: Int): Int {
        return xLeftMost + 2
    }

    override fun height(): Int {
        return 3
    }
}