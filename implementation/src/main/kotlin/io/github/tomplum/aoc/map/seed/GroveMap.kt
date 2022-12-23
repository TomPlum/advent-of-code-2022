package io.github.tomplum.aoc.map.seed

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class GroveMap(scan: List<String>) : AdventMap2D<GrovePatch>() {
    init {
        var x = 0
        var y = 0
        scan.forEach { row ->
            row.forEach { column ->
                val tile = GrovePatch(column)
                val position = Point2D(x, y)
                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }
    }

    fun getElfPositions() = filterTiles { tile -> tile.containsElf() }.keys

    fun getAdjacentTiles(pos: Point2D) = adjacentTiles(setOf(pos), GrovePatch('.'))

    fun moveElf(source: Point2D, target: Point2D) {
        removeTile(source)
        addTile(target, GrovePatch('#'))
    }

    fun getPatch(pos: Point2D) = getTile(pos)
}