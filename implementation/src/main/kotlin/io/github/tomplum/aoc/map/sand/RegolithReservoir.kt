package io.github.tomplum.aoc.map.sand

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class RegolithReservoir(scan: List<String>) : AdventMap2D<ReservoirTile>() {

    private val entryPoint = Point2D(500, 0)

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
        var sandPosition = entryPoint.shift(Direction.UP)

        var units = 0

        var sandFlowingIntoAbyss = false

        while(!sandFlowingIntoAbyss) {
            var atRest = false
            var tilesTraversed = 0
            while(!atRest && !sandFlowingIntoAbyss) {
                if (tilesTraversed > 1000) {
                    sandFlowingIntoAbyss = true
                }

                val tileBelow = getTile(sandPosition.shift(Direction.UP), ReservoirTile('.'))
                if (tileBelow.isSand() || tileBelow.isRock()) {
                    val tileDiagonallyLeft = getTile(sandPosition.shift(Direction.TOP_LEFT), ReservoirTile('.'))
                    if (tileDiagonallyLeft.isSand() || tileDiagonallyLeft.isRock()) {
                        val tileDiagonallyRight = getTile(sandPosition.shift(Direction.TOP_RIGHT), ReservoirTile('.'))
                        if (tileDiagonallyRight.isSand() || tileDiagonallyRight.isRock()) {
                            atRest = true
                        } else {
                            sandPosition = sandPosition.shift(Direction.TOP_RIGHT)
                            tilesTraversed += 1
                        }
                    } else {
                        sandPosition = sandPosition.shift(Direction.TOP_LEFT)
                        tilesTraversed += 1
                    }
                } else {
                    sandPosition = sandPosition.shift(Direction.UP)
                    tilesTraversed += 1
                }
            }

            if (!sandFlowingIntoAbyss) {
                addTile(sandPosition, ReservoirTile('o'))
                sandPosition = entryPoint.shift(Direction.UP)
                units += 1
            }
        }

        return units
    }

    inner class SandFlowingIntoAbyssException : Exception()
}