package io.github.tomplum.aoc.map.tree

import io.github.tomplum.libs.extensions.product
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.Direction.*
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import kotlin.properties.Delegates

class TreeMap(heightMapData: List<String>) : AdventMap2D<TreePatch>() {

    private val cardinalDirections = listOf(RIGHT, LEFT, DOWN, UP)

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


    fun getTreesVisibleFromOutside(): List<TreePatch> = getDataMap().filter { entry ->
        val tile = entry.toPair()
        if (tile.first.isEdge()) return@filter true

        cardinalDirections.map { direction -> direction.isTreeVisible(tile) }.any { visible -> visible }
    }.map { entry -> entry.value }

    fun getTreeScenicScores(): List<Int> = getDataMap().map { tile ->
        cardinalDirections.map { direction -> direction.viewingDistanceFrom(tile.toPair()) }.product()
    }

    private fun Direction.isTreeVisible(tile: Pair<Point2D, TreePatch>): Boolean {
        val (pos, tree) = tile

        var candidatePosition = pos.shift(this)

        while (hasRecorded(candidatePosition)) {
            val candidateTree = getTile(candidatePosition)
            if (candidateTree.height >= tree.height) {
                return false
            } else {
                candidatePosition = candidatePosition.shift(this)
            }
        }

        return true
    }

    private fun Direction.viewingDistanceFrom(tile: Pair<Point2D, TreePatch>): Int {
        val (pos, tree) = tile

        var viewingDistance = 0

        var candidatePosition = pos.shift(this)

        while (hasRecorded(candidatePosition)) {
            val candidateTree = getTile(candidatePosition)
            if (candidateTree.height < tree.height) {
                candidatePosition = candidatePosition.shift(this)
                viewingDistance += 1
            } else {
                viewingDistance += 1
                break
            }
        }

        return viewingDistance
    }

    private fun Point2D.isEdge(): Boolean {
       return this.x == xMin || this.x == xMax || this.y == yMin || this.y == yMax
    }
}