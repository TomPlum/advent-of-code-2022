package io.github.tomplum.aoc.map.valley

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.MapTile

class ValleyTile(val contents: List<Char>) : MapTile<Char>(if (contents.size == 1) contents.first() else contents.size.digitToChar()) {
    fun isWall() = contents.first() == '#'

    fun isClear() = !isWall() && !hasBlizzard()

    fun hasBlizzard() = contents.any { tile -> tile in listOf('>', '<', 'v', '^') }

    fun getBlizzardDirections() = contents.associateBy { direction ->
        when(direction) {
            '>' -> Direction.RIGHT
            '<' -> Direction.LEFT
            'v' -> Direction.UP
            '^' -> Direction.DOWN
            else -> throw IllegalArgumentException("Tile does not contain any blizzards")
        }
    }
}