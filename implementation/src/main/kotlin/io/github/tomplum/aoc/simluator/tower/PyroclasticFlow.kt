package io.github.tomplum.aoc.simluator.tower

import io.github.tomplum.aoc.simluator.tower.rocks.*
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class PyroclasticFlow(data: String): AdventMap2D<FlowTile>() {

    private val jetPattern = data
    private var jetIndex = 0
    var jetIsCycling = false

    private var rockIndex = 0
    var rockAreCycling = false
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

    fun hasAnyRocksResting(positions: List<Point2D>) = positions.any { pos -> hasRecorded(pos) }

    fun addRestingRock(positions: List<Point2D>) {
        if (hasAnyRocksResting(positions)) {
            throw IllegalArgumentException("Trying to add rocks to existing rock positions")
        }
        positions.forEach { pos -> addTile(pos, FlowTile('#')) }
    }

    fun getCycleMarkers(y: Int): List<Point2D> {
        return (1..7).map { x -> Point2D(x, y) }
    }

    fun addCycleMarker(pos: Point2D) {
        if (!hasRecorded(pos)) {
            addTile(pos, FlowTile('-'))
        }
    }

    fun getHighestRockPosition() = yMax()!!

    fun bump(height: Int) = getDataMap().keys.forEach { pos ->
        removeTile(pos)
        addTile(pos.shift(Direction.UP, height), FlowTile('#'))
    }

    fun getSnapshot() = getDataMap().keys

    fun getTopTwentyRows(): Int {
        val yMax = yMax()!!
        return (yMax downTo yMax - 20).flatMap {
            (1..7).map { x -> getTile(Point2D(x, yMax), FlowTile('.')) }
        }.joinToString("") {
            if (it.isRock()) "1" else "0"
        }.let { binary -> Integer.parseInt(binary, 2) }
    }
}