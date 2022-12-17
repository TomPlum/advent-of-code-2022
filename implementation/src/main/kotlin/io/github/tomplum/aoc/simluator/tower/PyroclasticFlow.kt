package io.github.tomplum.aoc.simluator.tower

import io.github.tomplum.aoc.simluator.tower.rocks.*
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class PyroclasticFlow(data: String): AdventMap2D<FlowTile>() {

    private val jetPattern = data
    private var jetIndex = 0

    private var rockIndex = 0
    private val rocks = listOf(
        HorizontalRock(),
        PlusRock(),
        AngleRock(),
        VerticalRock(),
        SquareRock()
    )

    fun getNextJetPatternDirection(): Direction {
        val direction = when(jetPattern[jetIndex]) {
            '>' -> Direction.RIGHT
            '<' -> Direction.LEFT
            else -> throw IllegalArgumentException("Unknown Jet Pattern Direction")
        }

        if (jetIndex == jetPattern.lastIndex) {
            jetIndex = 0
        } else {
            jetIndex += 1
        }

        return direction
    }

    fun getNextRock(): Rock {
        val rock = rocks[rockIndex]

        if (rockIndex == 4) {
            rockIndex = 0
        } else {
            rockIndex += 1
        }

        return rock
    }

    fun hasAnyRocksResting(positions: List<Point2D>) = positions.any { pos -> hasRecorded(pos) }

    fun addRestingRock(positions: List<Point2D>) {
        if (hasAnyRocksResting(positions)) {
            throw IllegalArgumentException("Trying to add rocks to existing rock positions")
        }
        positions.forEach { pos -> addTile(pos, FlowTile('#')) }
    }

    fun getHighestRockPosition() = yMax()!!
}