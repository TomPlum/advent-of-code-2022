package io.github.tomplum.aoc.map.hill

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import java.util.PriorityQueue

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
        return searchForBestSignalFrom { tile -> tile.isCurrentPosition() }
    }

    fun findShortestRouteFromLowestElevationToBestSignal(): Int {
        return searchForBestSignalFrom { tile -> tile.isLowestPossibleElevation() }
    }

    private fun searchForBestSignalFrom(startingTileFilter: (tile: HillTile) -> Boolean): Int {
        val distances = mutableMapOf<Point2D, Int>()
        val queue = PriorityQueue<Point2D>()

        filterTiles { tile -> startingTileFilter(tile) }.keys.forEach { position ->
            queue.offer(position)
            distances[position] = 0
        }

        while(queue.isNotEmpty()) {
            val currentPos = queue.poll()
            val distance = distances[currentPos]!!
            currentPos.traversableAdjacent().forEach { adjacentPos ->
                val updatedDistance = distance + 1
                if (updatedDistance < distances.getOrDefault(adjacentPos, Int.MAX_VALUE)) {
                    distances[adjacentPos] = updatedDistance
                    queue.add(adjacentPos)
                }
            }
        }

        val end = filterTiles { tile -> tile.isBestSignal() }.keys.first()
        return distances.filterKeys { pos -> pos == end }.values.min()
    }

    private fun Point2D.traversableAdjacent(): Set<Point2D> {
        return this.orthogonallyAdjacent()
            .filter { pos -> hasRecorded(pos) }
            .filter { dest -> getTile(this).canClimbTo(getTile(dest)) }
            .toSet()
    }
}