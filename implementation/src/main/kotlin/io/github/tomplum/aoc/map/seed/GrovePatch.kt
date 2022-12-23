package io.github.tomplum.aoc.map.seed

import io.github.tomplum.libs.math.map.MapTile

class GrovePatch(private val contents: Char) : MapTile<Char>(contents) {
    fun containsElf() = contents == '#'

    fun isEmpty() = !containsElf()
}