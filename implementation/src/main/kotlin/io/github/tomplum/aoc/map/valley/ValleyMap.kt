package io.github.tomplum.aoc.map.valley

import io.github.tomplum.libs.logging.AdventLogger
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class ValleyMap(data: List<String>) : AdventMap2D<ValleyTile>() {
    init {
        var x = 0
        var y = 0
        data.forEach { row ->
            row.forEach { column ->
                val tile = ValleyTile(listOf(column))
                val position = Point2D(x, y)
                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }
    }

    fun traverseBlizzards(): Int {
        val start = filterTiles { tile -> tile.isClear() }.minBy { (pos, _) -> pos.y }.key
        val goal = filterTiles { tile -> tile.isClear() }.maxBy { (pos, _) -> pos.y }.key

        var steps = 0

        val next = mutableSetOf<Point2D>()
        next.add(start)

        var lastPositions: MutableSet<Point2D>
        val visited = mutableSetOf<Point2D>()

        while(next.isNotEmpty() && !next.contains(goal)) {
            // Move Blizzards
            val currentBlizzardPositions = filterTiles { tile -> tile.hasBlizzard() }
            val newBlizzardPositions = currentBlizzardPositions.flatMap { (pos, tile) ->
                tile.getBlizzardDirections().map { (direction, symbol) ->
                    val newPosition = pos.shift(direction)
                    if (getTile(newPosition, ValleyTile(listOf('.'))).isWall()) {
                        newPosition.getNewBlizzardPosition(direction) to symbol
                    } else {
                        newPosition to symbol
                    }
                }
            }

            filterTiles { tile -> tile.hasBlizzard() }.keys.forEach { pos -> addTile(pos, ValleyTile(listOf('.'))) }
            newBlizzardPositions.groupBy { it.first }.forEach { (newPosition, blizzards) ->
                addTile(newPosition, ValleyTile(blizzards.map { it.second }))
            }

            // Move Person
            val adjacent = filterPoints(next.flatMap { pos -> pos.orthogonallyAdjacent() }.toSet())
            val traversable = adjacent.filterValues { tile -> tile.isClear() }.keys.filter { it !in visited }
            lastPositions = next.toMutableSet()
            visited.addAll(next)
            next.clear()

            next.addAll(traversable)
            next.addAll(lastPositions)


            steps += 1
            //AdventLogger.debug("Minute $steps\n$this\n")
        }

        return steps + 1
    }

    private fun Char.toDirection() = when(this) {
        '>' -> Direction.RIGHT
        '<' -> Direction.LEFT
        'v' -> Direction.UP
        '^' -> Direction.DOWN
        else -> throw IllegalArgumentException("Tile does not contain any blizzards")
    }

    private fun Point2D.getNewBlizzardPosition(direction: Direction): Point2D {
        if (direction == Direction.RIGHT) {
            return filterTiles { tile -> tile.isClear() || tile.hasBlizzard() }.keys
                .filter { pos -> pos.y == this.y }
                .minBy { pos -> pos.x }
        }

        if (direction == Direction.LEFT) {
            return filterTiles { tile -> tile.isClear() || tile.hasBlizzard() }.keys
                .filter { pos -> pos.y == this.y }
                .maxBy { pos -> pos.x }
        }

        if (direction == Direction.UP) {
            return filterTiles { tile -> tile.isClear() || tile.hasBlizzard() }.keys
                .filter { pos -> pos.x == this.x }
                .minBy { pos -> pos.y }
        }

        if (direction == Direction.DOWN) {
            return filterTiles { tile -> tile.isClear() || tile.hasBlizzard() }.keys
                .filter { pos -> pos.x == this.x }
                .maxBy { pos -> pos.y }
        }

        throw IllegalArgumentException("A blizzard cannot be facing [$direction]")
    }
}