package io.github.tomplum.aoc.map.hill

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import java.util.PriorityQueue

class HillHeightMap(data: List<String>) : AdventMap2D<HillTile>() {

    private val adjacencyMatrix = mutableMapOf<Point2D, Set<Point2D>>()

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
        return searchForBestSignalFrom { tile -> tile.isCurrentPosition }
    }

    fun findShortestRouteFromLowestElevationToBestSignal(): Int {
        return searchForBestSignalFrom { tile -> tile.isLowestPossibleElevation }
    }

    private fun searchForBestSignalFrom(startingTileFilter: (tile: HillTile) -> Boolean): Int {
        val distances = mutableMapOf<Point2D, Int>()
        val next = PriorityQueue<Point2D>()

        filterTiles { tile -> startingTileFilter(tile) }.keys.forEach { position ->
            next.offer(position)
            distances[position] = 0
        }

        while(next.isNotEmpty()) {
            val currentPos = next.poll()
            val distance = distances[currentPos]!!
            currentPos.traversableAdjacent().forEach { adjacentPos ->
                val updatedDistance = distance + 1
                if (updatedDistance < distances.getOrDefault(adjacentPos, Int.MAX_VALUE)) {
                    distances[adjacentPos] = updatedDistance
                    next.add(adjacentPos)
                }
            }
        }

        val end = filterTiles { tile -> tile.isBestSignal }.keys.first()
        return distances.filterKeys { pos -> pos == end }.values.min()
    }

    private fun Point2D.traversableAdjacent(): Set<Point2D> {
        val cached = adjacencyMatrix[this]

        if (cached == null) {
            val adjacent = this.orthogonallyAdjacent()
                .filter { pos -> hasRecorded(pos) }
                .filter { dest -> getTile(this).canClimbTo(getTile(dest)) }
                .toSet()
            adjacencyMatrix[this] = adjacent
            return adjacent
        }

        return cached
    }
}