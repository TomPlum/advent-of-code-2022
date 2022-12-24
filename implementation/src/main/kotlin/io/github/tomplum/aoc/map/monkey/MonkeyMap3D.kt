package io.github.tomplum.aoc.map.monkey

import io.github.tomplum.aoc.map.monkey.MonkeyMap3D.Face.*
import io.github.tomplum.extension.splitNewLine
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.Direction.*
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.map.AdventMap3D
import io.github.tomplum.libs.math.point.Point2D
import io.github.tomplum.libs.math.point.Point3D

class MonkeyMap3D(notes: List<String>): AdventMap2D<MonkeyMapTile>() {
    private val path = mutableListOf<Pair<Direction?, Int>>()

    private var aFace = mutableMapOf<Point2D, MonkeyMapTile>()
    private var bFace = mutableMapOf<Point2D, MonkeyMapTile>()
    private var cFace = mutableMapOf<Point2D, MonkeyMapTile>()
    private var dFace = mutableMapOf<Point2D, MonkeyMapTile>()
    private var eFace = mutableMapOf<Point2D, MonkeyMapTile>()
    private var fFace = mutableMapOf<Point2D, MonkeyMapTile>()

    init {
        val parts = notes.splitNewLine()
        val mapData = parts[0]

        var x = 0
        var y = 0
        mapData.forEach { row ->
            row.forEach { column ->
                x++
            }
            x = 0
            y++
        }

        val firstColumn = 0..49
        val middleColumn = 50..99
        val lastColumn = 100..149
        val firstRow = 0..49
        val secondRow = 50..99
        val thirdRow = 100..149
        val fourthRow = 150..199

        aFace = (middleColumn).flatMap { x ->
            (firstRow).map { y ->
                Point2D(x, y)
            }
        }.associateWith { pos -> getTile(pos) }.toMutableMap()
        bFace = (lastColumn).flatMap { x ->
            (firstRow).map { y ->
                Point2D(x, y)
            }
        }.associateWith { pos -> getTile(pos) }.toMutableMap()
        cFace = (middleColumn).flatMap { x ->
            (secondRow).map { y ->
                Point2D(x, y)
            }
        }.associateWith { pos -> getTile(pos) }.toMutableMap()
        dFace = (middleColumn).flatMap { x ->
            (thirdRow).map { y ->
                Point2D(x, y)
            }
        }.associateWith { pos -> getTile(pos) }.toMutableMap()
        eFace = (firstColumn).flatMap { x ->
            (thirdRow).map { y ->
                Point2D(x, y)
            }
        }.associateWith { pos -> getTile(pos) }.toMutableMap()
        fFace = (firstColumn).flatMap { x ->
            (fourthRow).map { y ->
                Point2D(x, y)
            }
        }.associateWith { pos -> getTile(pos) }.toMutableMap()

        var directionsString = parts[1].first().trim()
        while (directionsString.isNotEmpty()) {
            val (segment, next) = getNextPathSegment(directionsString)
            path += segment
            directionsString = next
        }
    }

    fun findFinalPassword(): Int {
        var position = filterTiles { tile -> tile.isTraversable() }
            .keys.sortedBy { pos -> pos.x }
            .minBy { pos -> pos.y }

        var facing = RIGHT
        val face = A

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

    private fun Point2D.getTargetFace(currentFace: Face, facing: Direction): Pair<Face, Direction> = when(currentFace) {
        A -> when(facing) {
            RIGHT -> Pair(B, RIGHT)
            DOWN -> Pair(F, RIGHT)
            LEFT -> Pair(E, RIGHT)
            UP -> Pair(C, UP)
            else -> throw IllegalArgumentException("You can be facing $facing")
        }
        B -> when(facing) {
            RIGHT -> Pair(D, LEFT)
            DOWN -> Pair(F, DOWN)
            LEFT -> Pair(A, LEFT)
            UP -> Pair(C, LEFT)
            else -> throw IllegalArgumentException("You can be facing $facing")
        }
        C -> when(facing) {
            RIGHT -> Pair(B, DOWN)
            DOWN -> Pair(D, UP)
            LEFT -> Pair(E, UP)
            UP -> Pair(D, UP)
            else -> throw IllegalArgumentException("You can be facing $facing")
        }
        D -> when(facing) {
            RIGHT -> Pair(B, LEFT)
            DOWN -> Pair(C, DOWN)
            LEFT -> Pair(E, LEFT)
            UP -> Pair(F, LEFT)
            else -> throw IllegalArgumentException("You can be facing $facing")
        }
        E -> when(facing) {
            RIGHT -> Pair(D, RIGHT)
            DOWN -> Pair(C, RIGHT)
            LEFT -> Pair(A, RIGHT)
            UP -> Pair(F, UP)
            else -> throw IllegalArgumentException("You can be facing $facing")
        }
        F -> when(facing) {
            RIGHT -> Pair(D, DOWN)
            DOWN -> Pair(E, DOWN)
            LEFT -> Pair(A, UP)
            UP -> Pair(B, UP)
            else -> throw IllegalArgumentException("You can be facing $facing")
        }
    }

    private fun Direction.value() = when (this) {
        RIGHT -> 0
        DOWN -> 1
        LEFT -> 2
        UP -> 3
        else -> throw IllegalArgumentException("Cannot get value for direction $this")
    }

    private fun Direction.symbol() = when (this) {
        RIGHT -> '>'
        DOWN -> 'v'
        LEFT -> '<'
        UP -> '^'
        else -> throw IllegalArgumentException("Cannot get symbol for direction $this")
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

    enum class Face {
        A, B, C, D, E, F
    }
}