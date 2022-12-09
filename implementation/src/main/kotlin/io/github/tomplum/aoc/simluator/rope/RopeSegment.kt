package io.github.tomplum.aoc.simluator.rope

import io.github.tomplum.libs.math.map.MapTile

/**
 * Represents a segment of rope in a [RopeBridge].
 * It could be the head, a knot or a nondescript segment of rope.
 * @param markers A list of markers on this segment. 0 is head, any others are knot indexes
 */
class RopeSegment(val markers: List<Int>) : MapTile<Int>(markers.first()) {
    /**
     * Checks if the segment contains the
     * head of the [RopeBridge].
     * @return true if contains the head, else false
     */
    fun isHead() = markers.contains(0)

    /**
     * Checks if the segment contains a knot
     * with the given [index] marker.
     * @param index The knot index to search for
     * @return true if contains the knot, else false
     */
    fun isKnot(index: Int) = markers.contains(index)
}