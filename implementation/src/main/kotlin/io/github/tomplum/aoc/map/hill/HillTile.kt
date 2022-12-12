package io.github.tomplum.aoc.map.hill

import io.github.tomplum.libs.math.map.MapTile

class HillTile(private val marker: Char) : MapTile<Char>(marker) {
    fun isCurrentPosition() = marker == 'S'

    fun isBestSignal() = marker == 'E'

    fun canClimbTo(dest: HillTile): Boolean {
        return !dest.isCurrentPosition() && dest.height() - this.height() <= 1
    }

    private fun height() = when {
        isCurrentPosition() -> 'a'.code
        isBestSignal() -> 'z'.code
        else -> marker.code
    }
}