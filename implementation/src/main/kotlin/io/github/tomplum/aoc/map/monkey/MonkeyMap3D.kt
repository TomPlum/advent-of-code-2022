package io.github.tomplum.aoc.map.monkey

import io.github.tomplum.aoc.map.monkey.CubeFace.*
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

    private val firstColumn = 0..49
    private val middleColumn = 50..99
    private val lastColumn = 100..149

    private val firstRow = 0..49
    private val secondRow = 50..99
    private val thirdRow = 100..149
    private val fourthRow = 150..199

    init {
        val parts = notes.splitNewLine()
        val mapData = parts[0]

        var xNet = 0
        var yNet = 0
        mapData.forEach { row ->
            row.forEach { column ->
                addTile(Point2D(xNet, yNet), MonkeyMapTile(column))
                xNet++
            }
            xNet = 0
            yNet++
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
                var potentialNewFacingDirection = facing
                var potentialNewFace = currentFace
                var candidatePosition = position.shift(facing)

                if (!candidatePosition.isInside(currentFace)) {
                    val (newFace, newPosition, newFacing) = stepRoundEdge(position, currentFace, facing)
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
                val rotated = facing.rotate(angle)
                val normalisedDirection = if (rotated == DOWN) UP else if (rotated == UP) DOWN else rotated
                facing = normalisedDirection
            }
        }

        return (1000 * (position.y + 1)) + (4 * (position.x + 1)) + facing.value()
    }

    private fun Point2D.offsetRelativeToNet(face: CubeFace) = when(face) {
        A -> Point2D(this.x + middleColumn.last, this.y)
        B -> Point2D(this.x + lastColumn.last, this.y)
        C -> Point2D(this.x + middleColumn.last, this.y + firstRow.last)
        D -> Point2D(this.x + middleColumn.last, this.y + secondRow.last)
        E -> Point2D(this.x, this.y + secondRow.last)
        F -> Point2D(this.x, this.y + thirdRow.last)
    }

    private fun Point2D.offsetRelativeToFace(face: CubeFace) = when(face) {
        A -> Point2D(abs(firstColumn.last - this.x), this.y)
        B -> Point2D(abs(middleColumn.last - this.x), this.y)
        C -> Point2D(abs(firstColumn.last - this.x), abs(firstRow.last - y))
        D -> Point2D(abs(firstColumn.last - this.x), abs(secondRow.last - y))
        E -> Point2D(this.x, abs(secondRow.last - y))
        F -> Point2D(this.x, abs(thirdRow.last - y))
    }

    private fun Point2D.isInside(face: CubeFace) = when(face) {
        A -> this in aFace
        B -> this in bFace
        C -> this in cFace
        D -> this in dFace
        E -> this in eFace
        F -> this in fFace
    }

    /**
     * Calculates the new position in the 2D net representation
     * of the cube faces.
     *   ╭╌╌╌┌───┬───┐
     *   ┊   │ A │ B │
     *   ┊╌╌╌├───┼───┘
     *   ┊   │ C │   ┊
     *   ┌───┼───┤╌╌╌┊
     *   │ E │ D │   ┊
     *   ├───┼───┘╌╌╌┊
     *   │ F │   ┊   ┊
     *   └───┘╌╌╌ ╌╌╌╯
     *   @param position The current position of the player
     *   @param currentFace The current face of the player
     *   @param facing The direction in which the player is currently facing
     *   @throws IllegalArgumentException if the facing direction is not cartesian.
     *   @return A triple of (Face stepped on to, new position, new facing direction) relative to the net
     */
    fun stepRoundEdge(position: Point2D, currentFace: CubeFace, facing: Direction): Triple<CubeFace, Point2D, Direction> = when(currentFace) {
        A -> when(facing) {
            RIGHT -> Triple(B, Point2D(position.x + 1, position.y), RIGHT)
            DOWN -> Triple(F, Point2D(0, fFace.yBottomMost() + (position.x - 50)), RIGHT)
            LEFT -> Triple(E, Point2D(0, eFace.yTopMost() - position.y), RIGHT)
            UP -> Triple(C, Point2D(position.x, position.y + 1), UP)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
        B -> when(facing) {
            RIGHT -> Triple(D, Point2D(dFace.xRightMost(), dFace.yTopMost() - position.y), LEFT)
            DOWN -> Triple(F, Point2D(position.x - 100, fFace.yTopMost()), DOWN)
            LEFT -> Triple(A, Point2D(position.x - 1, position.y), LEFT)
            UP -> Triple(C, Point2D(cFace.xRightMost(), cFace.yBottomMost() + (position.x - 100)), LEFT)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
        C -> when(facing) {
            RIGHT -> Triple(B, Point2D(bFace.xLeftMost() + (position.y - 50), bFace.yTopMost()), DOWN)
            DOWN -> Triple(A, Point2D(position.x, position.y - 1), DOWN)
            LEFT -> Triple(E, Point2D((position.y - 50), eFace.yBottomMost()), UP)
            UP -> Triple(D, Point2D(position.x, position.y + 1), UP)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
        D -> when(facing) {
            RIGHT -> Triple(B, Point2D(bFace.xRightMost(), bFace.yTopMost() - (position.y - 100)), LEFT)
            DOWN -> Triple(C, Point2D(position.x, position.y - 1), DOWN)
            LEFT -> Triple(E, Point2D(position.x - 1, position.y), LEFT)
            UP -> Triple(F, Point2D(fFace.xRightMost(), fFace.yBottomMost() + (position.x - 50)), LEFT)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
        E -> when(facing) {
            RIGHT -> Triple(D, Point2D(position.x + 1, position.y), RIGHT)
            DOWN -> Triple(C, Point2D(cFace.xLeftMost(), cFace.yBottomMost() + position.x), RIGHT)
            LEFT -> Triple(A, Point2D(aFace.xLeftMost(), aFace.yTopMost() - (position.y - 100)), RIGHT)
            UP -> Triple(F, Point2D(position.x, position.y + 1), UP)
            else -> throw IllegalArgumentException("You can't be facing $facing")
        }
        F -> when(facing) {
            RIGHT -> Triple(D, Point2D(dFace.xLeftMost() + (position.y - 150), dFace.yTopMost()), DOWN)
            DOWN -> Triple(E, Point2D(position.x, position.y - 1), DOWN)
            LEFT -> Triple(A, Point2D(aFace.xLeftMost() + (position.y - 150), 0), UP)
            UP -> Triple(B, Point2D(position.x + 100, 0), UP)
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
}