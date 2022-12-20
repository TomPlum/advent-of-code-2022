package io.github.tomplum.aoc.simluator.tower.rocks

import io.github.tomplum.aoc.simluator.tower.Rock
import io.github.tomplum.aoc.simluator.tower.RockType
import io.github.tomplum.libs.math.point.Point2D

class HorizontalRock : Rock {

    override val type: RockType
        get() = RockType.HORIZONTAL

    override fun positions(start: Point2D): List<Point2D> {
        return (start.x..start.x + 3).map { x -> Point2D(x, start.y) }
    }

    override fun xRightMost(xLeftMost: Int): Int {
        return xLeftMost + 3
    }

    override fun height(): Int {
        return 1
    }
}