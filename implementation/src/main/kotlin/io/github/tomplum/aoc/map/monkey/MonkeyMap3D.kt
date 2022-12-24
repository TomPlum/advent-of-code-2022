package io.github.tomplum.aoc.map.monkey

import io.github.tomplum.aoc.map.monkey.MonkeyMap3D.Face.*
import io.github.tomplum.extension.splitNewLine
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.Direction.*
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import kotlin.math.abs

class MonkeyMap3D(notes: List<String>): AdventMap2D<MonkeyMapTile>() {
    private val path = mutableListOf<Pair<Direction?, Int>>()

    private var aFace = mutableMapOf<Point2D, MonkeyMapTile>()
    private var bFace = mutableMapOf<Point2D, MonkeyMapTile>()
    private var cFace = mutableMapOf<Point2D, MonkeyMapTile>()
    private var dFace = mutableMapOf<Point2D, MonkeyMapTile>()
    private var eFace = mutableMapOf<Point2D, MonkeyMapTile>()
    private var fFace = mutableMapOf<Point2D, MonkeyMapTile>()

    val firstColumn = 0..49
    val middleColumn = 50..99
    val lastColumn = 100..149
    val firstRow = 0..49
    val secondRow = 50..99
    val thirdRow = 100..149
    val fourthRow = 150..199

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
        var position = Point2D(50, 0)

        var facing = RIGHT
        var currentFace = A

        path.forEach { (rotationDirection, distance) ->
            for (i in 0 until distance) {
                //val normalisedDirection = if (facing == DOWN) UP else if (facing == UP) DOWN else facing
                var potentialNewFacingDirection = facing
                var potentialNewFace = currentFace
                var candidatePosition = position.shift(facing)

                if (!candidatePosition.inside(currentFace)) {
                    val (newFace, newPosition, newFacing) = position.stepRoundEdge(currentFace, facing)
                    potentialNewFace = newFace
                    potentialNewFacingDirection = newFacing
                    candidatePosition = newPosition
                }

                if (getTile(candidatePosition).isWall()) {
                    break
                }

                if (getTile(candidatePosition).isTraversable()) {
                    position = candidatePosition
                    currentFace = potentialNewFace
                    facing = potentialNewFacingDirection
                }
            }

            if (rotationDirection != null) {
                val angle = if (rotationDirection == LEFT) -90 else 90
                facing = facing.rotate(angle)
            }
        }

        return (1000 * (position.y + 1)) + (4 * (position.x + 1)) + facing.value()
    }

    private fun Point2D.offsetRelativeToNet(face: Face) = when(face) {
        A -> Point2D(this.x + middleColumn.last, this.y)
        B -> Point2D(this.x + lastColumn.last, this.y)
        C -> Point2D(this.x + middleColumn.last, this.y + firstRow.last)
        D -> Point2D(this.x + middleColumn.last, this.y + secondRow.last)
        E -> Point2D(this.x, this.y + secondRow.last)
        F -> Point2D(this.x, this.y + thirdRow.last)
    }

    private fun Point2D.offsetRelativeToFace(face: Face) = when(face) {
        A -> Point2D(abs(firstColumn.last - this.x), this.y)
        B -> Point2D(abs(middleColumn.last - this.x), this.y)
        C -> Point2D(abs(firstColumn.last - this.x), abs(firstRow.last - y))
        D -> Point2D(abs(firstColumn.last - this.x), abs(secondRow.last - y))
        E -> Point2D(this.x, abs(secondRow.last - y))
        F -> Point2D(this.x, abs(thirdRow.last - y))
    }

    private fun Point2D.inside(face: Face) = when(face) {
        A -> this in aFace
        B -> this in bFace
        C -> this in cFace
        D -> this in dFace
        E -> this in eFace
        F -> this in fFace
    }

    private fun Point2D.stepRoundEdge(currentFace: Face, facing: Direction): Triple<Face, Point2D, Direction> = when(currentFace) {
        A -> when(facing) {
            RIGHT -> Triple(B, Point2D(this.x + 1, this.y), RIGHT)
            DOWN -> Triple(F, Point2D(this.x - 49, yMax()!!), RIGHT)
            LEFT -> Triple(E, Point2D(0, this.y + 98), RIGHT)
            UP -> Triple(C, Point2D(this.x, this.y + 1), UP)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
        B -> when(facing) {
            RIGHT -> Triple(D, Point2D(50, this.y + 98), LEFT)
            DOWN -> Triple(F, Point2D(this.x - 98, yMax()!!), DOWN)
            LEFT -> Triple(A, Point2D(this.x - 1, this.y), LEFT)
            UP -> Triple(C, Point2D(cFace.xRightMost(), (this.x - 98) + 49), LEFT)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
        C -> when(facing) {
            RIGHT -> Triple(B, Point2D(this.x + 1, this.y - 49), DOWN)
            DOWN -> Triple(A, Point2D(this.x, this.y - 1), DOWN)
            LEFT -> Triple(E, Point2D(eFace.xRightMost(), this.y + 49), UP)
            UP -> Triple(D, Point2D(this.x, this.y + 1), UP)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
        D -> when(facing) {
            RIGHT -> Triple(B, Point2D(bFace.xRightMost(), this.y - 98), LEFT)
            DOWN -> Triple(C, Point2D(this.x, this.y - 1), DOWN)
            LEFT -> Triple(E, Point2D(this.x - 1, this.y), LEFT)
            UP -> Triple(F, Point2D(this.x - 49, this.y + 1), LEFT)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
        E -> when(facing) {
            RIGHT -> Triple(D, Point2D(this.x + 1, this.y), RIGHT)
            DOWN -> Triple(C, Point2D(cFace.xLeftMost(), this.y - abs(49 - this.x)), RIGHT)
            LEFT -> Triple(A, Point2D(aFace.xLeftMost(), this.y - 98), RIGHT)
            UP -> Triple(F, Point2D(this.x, this.y + 1), UP)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
        F -> when(facing) {
            RIGHT -> Triple(D, Point2D((this.y - 149) + 49, dFace.yTopMost()), DOWN)
            DOWN -> Triple(E, Point2D(this.x, this.y - 1), DOWN)
            LEFT -> Triple(A, Point2D(this.x + 49, 0), UP)
            UP -> Triple(B, Point2D(this.x + 98, 0), UP)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
    }

    private fun MutableMap<Point2D, MonkeyMapTile>.xLeftMost() = this.keys.minBy { pos -> pos.x }.x
    private fun MutableMap<Point2D, MonkeyMapTile>.xRightMost() = this.keys.maxBy { pos -> pos.x }.x
    private fun MutableMap<Point2D, MonkeyMapTile>.yBottomMost() = this.keys.minBy { pos -> pos.y }.y
    private fun MutableMap<Point2D, MonkeyMapTile>.yTopMost() = this.keys.maxBy { pos -> pos.y }.y

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