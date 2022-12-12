package io.github.tomplum.aoc.map.hill

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import java.util.PriorityQueue

/**
 * The heightmap shows the local area from above broken into a grid;
 * the elevation of each square of the grid is given by a single lowercase
 * letter, where a is the lowest elevation, b is the next-lowest, and so
 * on up to the highest elevation, z.
 *
 * Also included on the heightmap are marks for your current position (S)
 * and the location that should get the best signal (E). Your current position (S)
 * has elevation a, and the location that should get the best signal (E) has elevation z.
 *
 * @param data A collection of lines that represent the map
 */
class HillHeightMap(data: List<String>) : AdventMap2D<HillTile>() {

    private val adjacencyMatrix = mutableMapOf<Point2D, Set<Point2D>>()
    private lateinit var bestSignalPosition: Point2D

    init {
        var x = 0
        var y = 0
        data.forEach { row ->
            row.forEach { column ->
                val tile = HillTile(column)
                val position = Point2D(x, y)

                if (tile.isBestSignal) {
                    bestSignalPosition = position
                }

                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }
    }

    /**
     * Finds the shortest route from the current position (S)
     * and the best signal position (E).
     * @return The number of steps taken to reach the target position
     */
    fun findShortestRouteToBestSignal(): Int {
        return searchForBestSignalFrom { tile -> tile.isCurrentPosition }
    }

    /**
     * Finds the shortest route from any of the lowest points of
     * elevation (a) or the current position (S) to the best signal
     * position (E).
     * @return The number of steps taken to reach the target position
     */
    fun findShortestRouteFromLowestElevationToBestSignal(): Int {
        return searchForBestSignalFrom { tile -> tile.isLowestPossibleElevation }
    }

    /**
     * Uses Dijkstra's algorithm to traverse the map and
     * find the shortest path from all the given tiles
     * that are yielded by the [startingTileFilter].
     * @param startingTileFilter A filter to produce the starting positions
     * @return The number of steps in the shortest path
     */
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

        return distances.filterKeys { pos -> pos == bestSignalPosition }.values.min()
    }

    /**
     * Finds all the traversable positions that are
     * orthogonally adjacent to this one.
     *
     * To avoid needing to get out your climbing gear,
     * the elevation of the destination square can be at
     * most one higher than the elevation of your current
     * square; that is, if your current elevation is m, you
     * could step to elevation n, but not to elevation o.
     * (This also means that the elevation of the destination
     * square can be much lower than the elevation of your
     * current square.)
     *
     * @return A collection of traversable adjacent points
     */
    private fun Point2D.traversableAdjacent(): Set<Point2D> {
        val cached = adjacencyMatrix[this]

        if (cached == null) {
            val adjacent = this.orthogonallyAdjacent()
                .filter { pos -> hasRecorded(pos) }
                .filter { dest -> getTile(this).canTraverseTo(getTile(dest)) }
                .toSet()
            adjacencyMatrix[this] = adjacent
            return adjacent
        }

        return cached
    }
}