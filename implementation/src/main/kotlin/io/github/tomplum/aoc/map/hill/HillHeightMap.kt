package io.github.tomplum.aoc.map.hill

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import java.util.Stack

class HillHeightMap(data: List<String>) : AdventMap2D<HillTile>() {

    private val visited = mutableSetOf<Point2D>()
    private var steps = 0
    private var hasFoundBestSignal = false

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
        val startingPosition = filterTiles { tile -> tile.isCurrentPosition() }.entries.first()
        return searchBfs(startingPosition.toPair())
    }

    private fun search(pair: Pair<Point2D, HillTile>): Int {
        val ( pos, tile ) = pair

        if (tile.isBestSignal()) {
            hasFoundBestSignal = true
            return steps
        }

        if (hasFoundBestSignal) {
            return steps
        }

        val next = Stack<Pair<Point2D, HillTile>>()
        visited.add(pos)

        val adjacentPositions = pos.orthogonallyAdjacent().filter { pos -> pos !in visited }.toSet()
        val adjacentTiles = filterPoints(adjacentPositions).filterValues { dest -> tile.canClimbTo(dest) }
        adjacentTiles.forEach { tile -> next.push(tile.toPair())}

        steps++

        while(next.isNotEmpty()) {
            search(next.pop())
        }

        steps--

        if (steps == 0) {
            visited.clear()
        }

        return 0
    }

    private fun searchBfs(start: Pair<Point2D, HillTile>): Int {
        val ( pos, tile ) = start

        var steps = 0
        val visited = mutableSetOf<Point2D>()
        val next = mutableSetOf(pos)

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

    private fun Set<Point2D>.adjacent(): Map<Point2D, HillTile> {
        return this.flatMap { pos -> pos.orthogonallyAdjacent() }
            .filter { pos -> hasRecorded(pos) }
            .associateWith { adj -> getTile(adj) }
    }
}