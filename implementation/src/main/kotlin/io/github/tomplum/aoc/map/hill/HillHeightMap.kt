package io.github.tomplum.aoc.map.hill

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class HillHeightMap(data: List<String>) : AdventMap2D<HillTile>() {

    private val traversableMap = mutableMapOf<Point2D, Map<Point2D, HillTile>>()
    private var shortestPath = Int.MAX_VALUE

    init {
        var x = 0
        var y = 0
        data.forEach { row ->
            row.forEach { column ->
                val position = Point2D(x, y)
                addTile(position, HillTile(column))
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
            .filterNot { distance -> distance == 0 }
            .minOf { distance -> distance }

    private fun search(start: Point2D): Int {
        var steps = 0
        val visited = mutableSetOf<Point2D>()
        val next = mutableSetOf(start)

        while(next.isNotEmpty()) {
            if (steps > shortestPath) {
                return 0
            }

            val adjacent = next.map { pos ->
                val traversable = pos.traversableAdjacent(start)
                traversableMap[pos] = traversable
                traversable
            }.reduce { a, b -> a + b }
            visited.addAll(next)
            next.clear()

            val traversable = adjacent.filterKeys { dest -> dest !in visited }
            next.addAll(traversable.keys)

            steps++

            if (adjacent.count { tile -> tile.value.isBestSignal() } == 1) {
                shortestPath = steps
                return steps
            }
        }

        return 0
    }

    private fun Point2D.traversableAdjacent(start: Point2D): Map<Point2D, HillTile> {
        return traversableMap[this] ?: this.orthogonallyAdjacent()
            .filter { pos -> hasRecorded(pos) }
            .filter { dest -> dest != start && getTile(this).canClimbTo(getTile(dest)) }
            .associateWith { adj -> getTile(adj) }
    }
}