package io.github.tomplum.aoc.map

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class TreeMap(heightMapData: List<String>) : AdventMap2D<TreePatch>() {
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
    }

    fun getTreesVisibleFromOutside(): List<TreePatch> {
        return getDataMap().filter { (pos, tree) ->
            val xMax = xMax()!!
            val yMax = yMax()!!
            val xMin = xMin()!!
            val yMin = yMin()!!

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
}