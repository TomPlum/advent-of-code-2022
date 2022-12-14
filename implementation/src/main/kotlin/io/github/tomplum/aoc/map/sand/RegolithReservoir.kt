package io.github.tomplum.aoc.map.sand

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.Direction.*
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class RegolithReservoir(scan: List<String>) : AdventMap2D<ReservoirTile>() {

    private val entryPoint = Point2D(500, 0)
    private val emptySpace = ReservoirTile('.')
    private val sand = ReservoirTile('o')

    init {
        scan.forEach { path ->
            path.trim().split(" -> ")
                .windowed(2)
                .flatMap { points ->
                    val firstParts = points[0].split(",")
                    val first = Point2D(firstParts[0].toInt(), firstParts[1].toInt())
                    val secondParts = points[1].split(",")
                    val second = Point2D(secondParts[0].toInt(), secondParts[1].toInt())

                    if (first.x != second.x) {
                        val xMin = listOf(first.x, second.x).min()
                        val xMax = listOf(first.x, second.x).max()
                        IntRange(xMin, xMax).map { x -> Point2D(x, first.y) }
                    } else {
                        val yMin = listOf(first.y, second.y).min()
                        val yMax = listOf(first.y, second.y).max()
                        IntRange(yMin, yMax).map { y -> Point2D(first.x, y) }
                    }
                }
                .forEach { point -> addTile(point, ReservoirTile('#')) }
        }

        addTile(entryPoint, ReservoirTile('+'))
    }

    fun produceSand(): Int {
        var sandPosition = entryPoint

        var units = 0

        var sandFlowingIntoAbyss = false

        while(!sandFlowingIntoAbyss) {
            var atRest = false
            var tilesTraversed = 0
            while(!atRest && !sandFlowingIntoAbyss) {
                if (tilesTraversed > 180) {
                    sandFlowingIntoAbyss = true
                }

                val positionBelow = sandPosition.shift(UP)
                val tileBelow = getTile(positionBelow, emptySpace)
                val (isNowResting, newSandPosition, newTilesTraversed) = checkForBlockingTiles(
                    tileBelow,
                    sandPosition,
                    tilesTraversed,
                    positionBelow
                )
                atRest = isNowResting
                sandPosition = newSandPosition
                tilesTraversed = newTilesTraversed
            }

            if (!sandFlowingIntoAbyss) {
                addTile(sandPosition, ReservoirTile('o'))
                sandPosition = entryPoint
                units += 1
            }
        }

        return units
    }

    fun produceSandWithFloor(): Int {
        var sandPosition = entryPoint
        val yFloor = yMax()!! + 2

        var units = 0

        var sandBlockingSource = false

        while(!sandBlockingSource) {
            var atRest = false

            while(!atRest && !sandBlockingSource) {
                val positionBelow = sandPosition.shift(UP)
                val tileBelow = getTile(positionBelow, emptySpace)

                if (getTile(entryPoint, emptySpace).isSand()) {
                    sandBlockingSource = true
                }

                if (positionBelow.y == yFloor) {
                    atRest = true
                } else {
                    val (isNowResting, newSandPosition) = checkForBlockingTiles(
                        tileBelow,
                        sandPosition,
                        0,
                        positionBelow
                    )
                    atRest = isNowResting
                    sandPosition = newSandPosition
                }
            }

            if (!sandBlockingSource) {
                addTile(sandPosition, sand)
                sandPosition = entryPoint
                units += 1
            }
        }

        return units
    }

    private fun checkForBlockingTiles(
        tileBelow: ReservoirTile,
        sandPosition: Point2D,
        tilesTraversed: Int,
        positionBelow: Point2D
    ): Triple<Boolean, Point2D, Int> {
        var newSandPosition = sandPosition
        var atRest = false
        var newTilesTraversed = tilesTraversed

        if (tileBelow.isOccupied()) {
            val tileDiagonallyLeft = getTile(newSandPosition.shift(TOP_LEFT), emptySpace)
            if (tileDiagonallyLeft.isOccupied()) {
                val tileDiagonallyRight = getTile(newSandPosition.shift(TOP_RIGHT), emptySpace)
                if (tileDiagonallyRight.isOccupied()) {
                    atRest = true
                } else {
                    newSandPosition = newSandPosition.shift(TOP_RIGHT)
                    newTilesTraversed += 1
                }
            } else {
                newSandPosition = newSandPosition.shift(TOP_LEFT)
                newTilesTraversed += 1
            }
        } else {
            newSandPosition = positionBelow
            newTilesTraversed += 1
        }

        return Triple(atRest, newSandPosition, newTilesTraversed)
    }

}