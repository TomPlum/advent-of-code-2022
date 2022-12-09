package io.github.tomplum.aoc.simluator.rope

import io.github.tomplum.libs.math.map.MapTile

class Knot(private val marker: Char) : MapTile<Char>(marker) {

    private var visited = false

    fun isHead() = marker == 'H'

    fun isTail() = marker == 'T'

    fun isBoth() = marker == 'B'
}