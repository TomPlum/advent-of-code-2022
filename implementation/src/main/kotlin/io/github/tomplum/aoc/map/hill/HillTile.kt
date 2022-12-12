package io.github.tomplum.aoc.map.hill

import io.github.tomplum.libs.math.map.MapTile

class HillTile(marker: Char) : MapTile<Char>(marker) {

    val isBestSignal = marker == 'E'
    val isCurrentPosition = marker == 'S'
    val isLowestPossibleElevation = marker == 'a' || isCurrentPosition

    private val height = when {
        isCurrentPosition -> 'a'.code
        isBestSignal -> 'z'.code
        else -> marker.code
    }

    fun canClimbTo(dest: HillTile): Boolean {
        return dest.height - this.height <= 1
    }
}