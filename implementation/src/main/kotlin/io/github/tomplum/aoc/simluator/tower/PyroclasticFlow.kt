package io.github.tomplum.aoc.simluator.tower

import io.github.tomplum.aoc.simluator.tower.rocks.*
import io.github.tomplum.extension.lcm
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.point.Point2D

class PyroclasticFlow(data: String) {

    private val rockPositions = mutableMapOf<Point2D, Char>()

    private val jetPattern = data.map { direction ->
        when (direction) {
            '>' -> Direction.RIGHT
            '<' -> Direction.LEFT
            else -> throw IllegalArgumentException("Unknown Jet Pattern Direction")
        }
    }.toTypedArray()

    var jetIndex = 0
    private var jetIsCycling = false

    var rockIndex = 0
    private var rockAreCycling = false

    private val rocks = listOf(
        HorizontalRock(),
        PlusRock(),
        AngleRock(),
        VerticalRock(),
        SquareRock()
    )

    var highestRockPosition = 0

    fun getNextJetPatternDirection(): Direction {
        val direction = jetPattern[jetIndex]

        if (jetIndex == jetPattern.lastIndex) {
            jetIndex = 0
            jetIsCycling = true
        } else {
            jetIndex += 1
            jetIsCycling = false
        }

        return direction
    }

    fun getNextRock(): Rock {
        val rock = rocks[rockIndex]

        if (rockIndex == 4) {
            rockIndex = 0
            rockAreCycling = true
        } else {
            rockIndex += 1
            rockAreCycling = false
        }

        return rock
    }

    fun hasAnyRocksResting(positions: List<Point2D>) = positions.any { pos ->
        rockPositions.containsKey(pos)
    }

    fun addRestingRock(positions: List<Point2D>) = positions.forEach { pos ->
        if (pos.y > highestRockPosition) {
            highestRockPosition = pos.y
        }
        rockPositions[pos] = '#'
    }

}