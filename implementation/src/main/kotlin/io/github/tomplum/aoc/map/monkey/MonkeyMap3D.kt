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

        path.forEach { (direction, distance) ->
            for (i in 0 until distance) {
                val normalisedDirection = if (facing == DOWN) UP else if (facing == UP) DOWN else facing
                var potentialNewFacingDirection = normalisedDirection
                var relativeCandidatePosition = position.shift(normalisedDirection)

                if (getTile(relativeCandidatePosition, MonkeyMapTile(' ')).isVoid()) {
                    val (newFace, newPosition, newFacing) = position.offsetRelativeToFace(currentFace).getTargetFace(currentFace, normalisedDirection)
                    currentFace = newFace
                    potentialNewFacingDirection = newFacing
                    relativeCandidatePosition = newPosition.offsetRelativeToNet(newFace)
                }

                if (getTile(relativeCandidatePosition).isWall()) {
                    break
                }

                if (getTile(relativeCandidatePosition).isTraversable()) {
                    position = relativeCandidatePosition
                    facing = potentialNewFacingDirection
                }
            }

            if (direction != null) {
                val angle = if (direction == LEFT) -90 else 90
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

    private fun Point2D.getTargetFace(currentFace: Face, facing: Direction): Triple<Face, Point2D, Direction> = when(currentFace) {
        A -> when(facing) {
            RIGHT -> Triple(B, Point2D(bFace.xLeftMost(), this.y), RIGHT)
            DOWN -> Triple(F, Point2D(fFace.xLeftMost(), this.x), RIGHT)
            LEFT -> Triple(E, Point2D(eFace.xLeftMost(), abs(this.y - 49)), RIGHT)
            UP -> Triple(C, Point2D(this.x, cFace.yBottomMost()), UP)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
        B -> when(facing) {
            RIGHT -> Triple(D, Point2D(dFace.xRightMost(), abs(this.y - 49)), LEFT)
            DOWN -> Triple(F, Point2D(this.x, fFace.yTopMost()), DOWN)
            LEFT -> Triple(A, Point2D(aFace.xRightMost(), this.y), LEFT)
            UP -> Triple(C, Point2D(cFace.xRightMost(), this.x), LEFT)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
        C -> when(facing) {
            RIGHT -> Triple(B, Point2D(this.y, bFace.yTopMost()), DOWN)
            DOWN -> Triple(A, Point2D(this.x, aFace.yTopMost()), DOWN)
            LEFT -> Triple(E, Point2D(this.y, eFace.yBottomMost()), UP)
            UP -> Triple(D, Point2D(this.x, dFace.yBottomMost()), UP)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
        D -> when(facing) {
            RIGHT -> Triple(B, Point2D(bFace.xRightMost(), abs(this.y - 49)), LEFT)
            DOWN -> Triple(C, Point2D(this.x, cFace.yTopMost()), DOWN)
            LEFT -> Triple(E, Point2D(eFace.xRightMost(), this.y), LEFT)
            UP -> Triple(F, Point2D(fFace.xRightMost(), this.x), LEFT)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
        E -> when(facing) {
            RIGHT -> Triple(D, Point2D(dFace.xLeftMost(), this.y), RIGHT)
            DOWN -> Triple(C, Point2D(cFace.xLeftMost(), this.x), RIGHT)
            LEFT -> Triple(A, Point2D(aFace.xLeftMost(), abs(this.y - 49)), RIGHT)
            UP -> Triple(F, Point2D(this.x, fFace.yBottomMost()), UP)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
        F -> when(facing) {
            RIGHT -> Triple(D, Point2D(this.y, dFace.yTopMost()), DOWN)
            DOWN -> Triple(E, Point2D(this.x, eFace.yTopMost()), DOWN)
            LEFT -> Triple(A, Point2D(this.y, aFace.yBottomMost()), UP)
            UP -> Triple(B, Point2D(this.x, bFace.yBottomMost()), UP)
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