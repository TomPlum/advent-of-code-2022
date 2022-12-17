package io.github.tomplum.aoc.simluator.tower

import io.github.tomplum.libs.math.map.MapTile

class FlowTile(private val symbol: Char) : MapTile<Char>(symbol) {
    fun isRock() = symbol == '#'
}