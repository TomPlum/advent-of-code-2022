package io.github.tomplum.aoc.map.hill

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class HillHeightMap(data: List<String>) : AdventMap2D<HillTile>() {

    init {
        var x = 0
        var y = 0
        data.forEach { row ->
            row.forEach { column ->
                addTile(Point2D(x, y), HillTile(column))
                x++
            }
            x = 0
            y++
        }
    }

    fun findShortestRouteToBestSignal(): Int {
        val startingPosition = filterTiles { tile -> tile.isCurrentPosition() }.entries.first().key
        return search(startingPosition)
    }

    fun findShortestRouteFromLowestElevationToBestSignal(): Int =
        filterTiles { tile -> tile.isLowestPossibleElevation() }
            .map { tile -> tile.key }
            .map { startingPosition -> search(startingPosition) }
            .minOf { distance -> distance }

    private fun search(start: Point2D): Int {
        var steps = 0
        val visited = mutableSetOf<Point2D>()
        val next = mutableSetOf(start)

        while(next.isNotEmpty()) {
            val adjacent = next.map { pos -> pos.traversableAdjacent() }.reduce { a, b -> a + b }
            visited.addAll(next)
            next.clear()

            val traversable = adjacent.filterKeys { dest -> dest !in visited }
            next.addAll(traversable.keys)

            steps++

            if (adjacent.count { tile -> tile.value.isBestSignal() } == 1) {
                return steps
            }
        }

        return steps
    }

    private fun Point2D.traversableAdjacent(): Map<Point2D, HillTile> {
        return this.orthogonallyAdjacent()
            .filter { pos -> hasRecorded(pos) }
            .filter { dest -> getTile(this).canClimbTo(getTile(dest)) }
            .associateWith { adj -> getTile(adj) }
    }
}