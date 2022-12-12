package io.github.tomplum.aoc.map.hill

import io.github.tomplum.libs.math.map.MapTile

/**
 * Represents a single tile in a [HillHeightMap].
 * @param marker The position and/or height information marker
 */
class HillTile(marker: Char) : MapTile<Char>(marker) {

    val isBestSignal = marker == 'E'
    val isCurrentPosition = marker == 'S'
    val isLowestPossibleElevation = marker == 'a' || isCurrentPosition

    private val height = when {
        isCurrentPosition -> 'a'.code
        isBestSignal -> 'z'.code
        else -> marker.code
    }

    /**
     * Checks if it is possible to traverse from
     * this tile to the given [destination].
     * @param destination The target tile.
     * @return true if traversable, else false
     */
    fun canTraverseTo(destination: HillTile): Boolean {
        return destination.height - this.height <= 1
    }
}