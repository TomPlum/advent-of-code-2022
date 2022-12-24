package io.github.tomplum.aoc.map.monkey

import io.github.tomplum.extension.splitNewLine
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.Direction.*
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class MonkeyMap(notes: List<String>) : AdventMap2D<MonkeyMapTile>() {

    private val path = mutableListOf<Pair<Direction?, Int>>()

    init {
        val parts = notes.splitNewLine()
        val mapData = parts[0]
        var x = 0
        var y = 0
        mapData.forEach { row ->
            row.forEach { column ->
                addTile(Point2D(x, y), MonkeyMapTile(column))
                x++
            }
            x = 0
            y++
        }


        var directionsString = parts[1].first().trim()

        while (directionsString.isNotEmpty()) {
            val (segment, next) = getNextPathSegment(directionsString)
            path += segment
            directionsString = next
        }
    }

    fun findFinalPassword(): Int {
        var facing = RIGHT

        var position = filterTiles { tile -> tile.isTraversable() }
            .keys.sortedBy { pos -> pos.x }
            .minBy { pos -> pos.y }

        path.forEach { (direction, distance) ->
            for (i in 0 until distance) {
                val normalisedDirection = if (facing == DOWN) UP else if (facing == UP) DOWN else facing
                var candidatePos = position.shift(normalisedDirection)
                if (getTile(candidatePos, MonkeyMapTile(' ')).isVoid()) {
                    if (facing == RIGHT) {
                        candidatePos = filterTiles { tile -> !tile.isVoid() }.keys
                            .filter { pos -> pos.y == position.y }
                            .minBy { pos -> pos.x }
                    }

                    if (facing == LEFT) {
                        candidatePos = filterTiles { tile -> !tile.isVoid() }.keys
                            .filter { pos -> pos.y == position.y }
                            .maxBy { pos -> pos.x }
                    }

                    if (facing == UP) {
                        candidatePos = filterTiles { tile -> !tile.isVoid() }.keys
                            .filter { pos -> pos.x == position.x }
                            .maxBy { pos -> pos.y }
                    }

                    if (facing == DOWN) {
                        candidatePos = filterTiles { tile -> !tile.isVoid() }.keys
                            .filter { pos -> pos.x == position.x }
                            .minBy { pos -> pos.y }
                    }
                }

                if (getTile(candidatePos).isWall()) {
                    break
                }

                if (hasRecorded(candidatePos) && getTile(candidatePos).isTraversable()) {
                    position = candidatePos
                }
            }

            if (direction != null) {
                val angle = if (direction == LEFT) -90 else 90
                facing = facing.rotate(angle)
            }
        }

        return (1000 * (position.y + 1)) + (4 * (position.x + 1)) + facing.value()
    }

    private fun Direction.value() = when (this) {
        RIGHT -> 0
        DOWN -> 1
        LEFT -> 2
        UP -> 3
        else -> throw IllegalArgumentException("Cannot get value for direction $this")
    }

    private fun getNextPathSegment(string: String): Pair<Pair<Direction?, Int>, String> {
        val distance = string.takeWhile { char -> char.isDigit() }
        val remaining = string.removePrefix(distance)
        if (remaining.isEmpty()) {
            return Pair(null, distance.toInt()) to ""
        }

        val direction = when (remaining.first()) {
            'R' -> RIGHT
            'L' -> LEFT
            else -> throw IllegalArgumentException("Invalid directional character")
        }

        return Pair(direction, distance.toInt()) to string.removePrefix("$distance${remaining.first()}")
    }
}