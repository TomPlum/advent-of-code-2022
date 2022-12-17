package io.github.tomplum.aoc.simluator.tower

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class PyroclasticFlow(data: String): AdventMap2D<FlowTile>() {

    private val jetPattern = data
    private var jetIndex = 0

    private var rockTypeIndex = 0

    fun getNextJetPatternDirection(): Direction {
        if (jetIndex == jetPattern.lastIndex) {
            jetIndex = 0
        }

        val direction = when(jetPattern[jetIndex]) {
            '>' -> Direction.RIGHT
            '<' -> Direction.LEFT
            else -> throw IllegalArgumentException("Unknown Jet Pattern Direction")
        }

        jetIndex += 1

        return direction
    }

    fun getNextRock(): RockType {
        if (rockTypeIndex == 4) {
            rockTypeIndex = 0
        }

        val rockType = RockType.values()[rockTypeIndex]
        rockTypeIndex += 1

        return rockType
    }

    fun hasAnyRocksResting(positions: List<Point2D>) = positions.any { pos -> hasRecorded(pos) }

    fun addRestingRock(positions: List<Point2D>) = positions.forEach { pos -> addTile(pos, FlowTile('#')) }

    fun getHighestRockPosition() = yMax()!!
}