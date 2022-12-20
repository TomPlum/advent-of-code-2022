package io.github.tomplum.aoc.simluator.tower

import io.github.tomplum.aoc.simluator.tower.rocks.*
import io.github.tomplum.extension.lcm
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.point.Point2D

class PyroclasticFlow(data: String) {

    private val rockPositions = mutableMapOf<Point2D, Char>()

    val jetPattern = data.map { direction ->
        when (direction) {
            '>' -> Direction.RIGHT
            '<' -> Direction.LEFT
            else -> throw IllegalArgumentException("Unknown Jet Pattern Direction")
        }
    }.toTypedArray()

    var jetIndex = 0
    var jetIsCycling = false

    var rockIndex = 0
    var rockAreCycling = false

    val maxHeights = LongArray(7) { 0L }

    val lcm = listOf(5, jetPattern.size).map { it.toLong() }.lcm()

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

    fun ceiling(): List<Int> = rockPositions.keys
        .groupBy { pos -> pos.x }
        .entries
        .sortedBy { (x, _) -> x }
        .map { points -> points.value.maxBy { point -> point.y } }
        .let { points ->
            if (points.isEmpty()) {
                return listOf(0, 0, 0, 0, 0, 0, 0)
            }
            val normalTo = points.maxOf { pos -> pos.y }
            points.map { point -> normalTo - point.y }
        }

    fun hasAnyRocksResting(positions: List<Point2D>) = positions.any { pos ->
        rockPositions.containsKey(pos)
    }

    fun addRestingRock(positions: List<Point2D>) = positions.forEach { pos ->
        if (pos.y > highestRockPosition) {
            highestRockPosition = pos.y
        }
        maxHeights[pos.x - 1] = maxHeights[pos.x - 1] + pos.y
        rockPositions[pos] = '#'
    }

}