package io.github.tomplum.aoc.simluator.tower.rocks

import io.github.tomplum.aoc.simluator.tower.Rock
import io.github.tomplum.aoc.simluator.tower.RockType
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.point.Point2D

class SquareRock : Rock {

    override val type: RockType
        get() = RockType.SQUARE

    override fun positions(start: Point2D): List<Point2D> {
        val topRight = start.shift(Direction.RIGHT)
        val bottomLeft = start.shift(Direction.DOWN)
        val bottomRight = bottomLeft.shift(Direction.RIGHT)
        return listOf(start, topRight, bottomLeft, bottomRight)
    }

    override fun xRightMost(xLeftMost: Int): Int {
        return xLeftMost + 1
    }

    override fun height(): Int {
        return 2
    }
}