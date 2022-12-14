package io.github.tomplum.aoc.map.sand

import io.github.tomplum.libs.math.map.MapTile

class ReservoirTile(private val contents: Char): MapTile<Char>(contents) {
    fun isSand() = contents == 'o'

    fun isRock() = contents == '#'
}