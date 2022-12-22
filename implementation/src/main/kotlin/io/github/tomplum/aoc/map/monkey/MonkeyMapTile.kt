package io.github.tomplum.aoc.map.monkey

import io.github.tomplum.libs.math.map.MapTile

class MonkeyMapTile(value: Char) : MapTile<Char>(value) {
    fun isTraversable() = value == '.'

    fun isVoid() = value == ' '

    fun isWall() = value == '#'
}