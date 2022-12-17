package io.github.tomplum.aoc.simluator.tower.rocks

import io.github.tomplum.aoc.simluator.tower.Rock
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.point.Point2D

class PlusRock : Rock {
    override fun positions(start: Point2D): List<Point2D> {
        val top = start.shift(Direction.RIGHT)
        val left = start.shift(Direction.DOWN)
        val middle = left.shift(Direction.RIGHT)
        val right = middle.shift(Direction.RIGHT)
        val bottom = middle.shift(Direction.DOWN)
        return listOf(top, left, middle, right, bottom)
    }

    override fun xRightMost(xLeftMost: Int): Int {
        return xLeftMost + 2
    }

    override fun height(): Int {
        return 3
    }
}