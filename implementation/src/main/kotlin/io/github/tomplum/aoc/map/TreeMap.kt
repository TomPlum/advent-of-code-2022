package io.github.tomplum.aoc.map

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import kotlin.properties.Delegates

class TreeMap(heightMapData: List<String>) : AdventMap2D<TreePatch>() {

    private var xMax by Delegates.notNull<Int>()
    private var yMax by Delegates.notNull<Int>()
    private var xMin by Delegates.notNull<Int>()
    private var yMin by Delegates.notNull<Int>()

    init {
        var x = 0
        var y = 0
        heightMapData.forEach { row ->
            row.forEach { column ->
                addTile(Point2D(x, y), TreePatch(column.toString().toInt()))
                x++
            }
            x = 0
            y++
        }
        xMax = xMax()!!
        yMax = yMax()!!
        xMin = xMin()!!
        yMin = yMin()!!
    }

    fun getTreesVisibleFromOutside(): List<TreePatch> {
        return getDataMap().filter { (pos, tree) ->
            if (pos.x == xMin || pos.x == xMax || pos.y == yMin || pos.y == yMax) {
                return@filter true
            }

            var xPositive = pos.x + 1
            while (xPositive <= xMax) {
                val candidatePos = Point2D(xPositive, pos.y)
                val candidateTree = getTile(candidatePos)
                if (candidateTree.height < tree.height) {
                    if (candidatePos.x == xMax) {
                        return@filter true
                    }
                    xPositive += 1
                } else {
                    break
                }
            }

            var xNegative = pos.x - 1
            while(xNegative >= xMin) {
                val candidatePos = Point2D(xNegative, pos.y)
                val candidateTree = getTile(candidatePos)
                if (candidateTree.height < tree.height) {
                    if (candidatePos.x == xMin) {
                        return@filter true
                    }
                    xNegative -= 1
                } else {
                    break
                }
            }

            var yPositive = pos.y + 1
            while(yPositive <= yMax) {
                val candidatePos = Point2D(pos.x, yPositive)
                val candidateTree = getTile(candidatePos)
                if (candidateTree.height < tree.height) {
                    if (candidatePos.y == yMax) {
                        return@filter true
                    }
                    yPositive += 1
                } else {
                    break
                }
            }

            var yNegative = pos.y - 1
            while(yNegative >= yMin) {
                val candidatePos = Point2D(pos.x, yNegative)
                val candidateTree = getTile(candidatePos)
                if (candidateTree.height < tree.height) {
                    if (candidatePos.y == yMin) {
                        return@filter true
                    }
                    yNegative -= 1
                } else {
                    break
                }
            }

            false
        }.map { it.value }
    }

    fun getTreeScenicScores(): List<Int> {
        return getDataMap().map { tile ->
            val xPositiveViewingDistance = calculateViewingDistanceFrom(tile.toPair(), Direction.RIGHT)
            val xNegativeViewingDistance = calculateViewingDistanceFrom(tile.toPair(), Direction.LEFT)

            val yPositiveViewingDistance = calculateViewingDistanceFrom(tile.toPair(), Direction.DOWN)
            val yNegativeViewingDistance = calculateViewingDistanceFrom(tile.toPair(), Direction.UP)

            // Scenic score is up * left * down * right
            yNegativeViewingDistance * xNegativeViewingDistance * yPositiveViewingDistance * xPositiveViewingDistance
        }
    }

    private fun calculateViewingDistanceFrom(tile: Pair<Point2D, TreePatch>, direction: Direction): Int {
        val (pos, tree) = tile

        var viewingDistance = 0

        var candidatePosition = pos.shift(direction)

        while (hasRecorded(candidatePosition)) {
            val candidateTree = getTile(candidatePosition)
            if (candidateTree.height < tree.height) {
                candidatePosition = candidatePosition.shift(direction)
                viewingDistance += 1
            } else {
                viewingDistance += 1
                break
            }
        }

        return viewingDistance
    }
}