package io.github.tomplum.aoc.simluator.tower.rocks

import io.github.tomplum.aoc.simluator.tower.Rock
import io.github.tomplum.libs.math.point.Point2D

class VerticalRock : Rock {
    override fun positions(start: Point2D): List<Point2D> {
        return (start.y downTo start.y - 3).map { y -> Point2D(start.x, y) }
    }

    override fun xRightMost(xLeftMost: Int): Int {
        return xLeftMost
    }

    override fun height(): Int {
        return 4
    }
}