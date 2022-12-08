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

    fun getTreeScenicScores(): List<Int> {
        return getDataMap().map { (pos, tree) ->
            val xMax = xMax()!!
            val yMax = yMax()!!
            val xMin = xMin()!!
            val yMin = yMin()!!

            var xPositiveViewingDistance = 0
            var xPositive = pos.x + 1
            while (xPositive <= xMax) {
                val candidatePos = Point2D(xPositive, pos.y)
                val candidateTree = getTile(candidatePos)
                if (candidateTree.height < tree.height) {
                    xPositive += 1
                    xPositiveViewingDistance += 1
                } else if (candidateTree.height == tree.height) {
                    xPositiveViewingDistance += 1
                    break
                } else {
                    xPositiveViewingDistance += 1
                    break
                }
            }

            var xNegativeViewingDistance = 0
            var xNegative = pos.x - 1
            while(xNegative >= xMin) {
                val candidatePos = Point2D(xNegative, pos.y)
                val candidateTree = getTile(candidatePos)
                if (candidateTree.height < tree.height) {
                    xNegative -= 1
                    xNegativeViewingDistance += 1
                } else if (candidateTree.height == tree.height) {
                    xNegativeViewingDistance += 1
                    break
                } else {
                    break
                }
            }

            var yPositiveViewingDistance = 0
            var yPositive = pos.y + 1
            while(yPositive <= yMax) {
                val candidatePos = Point2D(pos.x, yPositive)
                val candidateTree = getTile(candidatePos)
                if (candidateTree.height < tree.height) {
                    yPositive += 1
                    yPositiveViewingDistance += 1
                } else if (candidateTree.height == tree.height) {
                    yPositiveViewingDistance += 1
                    break
                } else {
                    xNegativeViewingDistance += 1
                    break
                }
            }

            var yNegativeViewingDistance = 0
            var yNegative = pos.y - 1
            while(yNegative >= yMin) {
                val candidatePos = Point2D(pos.x, yNegative)
                val candidateTree = getTile(candidatePos)
                if (candidateTree.height < tree.height) {
                    yNegative -= 1
                    yNegativeViewingDistance += 1
                } else if (candidateTree.height == tree.height) {
                    yNegativeViewingDistance += 1
                    break
                } else {
                    yNegativeViewingDistance += 1
                    break
                }
            }

            // Scenic score is up * left * down * right
            yNegativeViewingDistance * xNegativeViewingDistance * yPositiveViewingDistance * xPositiveViewingDistance
        }
    }
}