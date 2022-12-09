package io.github.tomplum.aoc.simluator.rope

import io.github.tomplum.libs.math.map.MapTile

class RopeSegment(val markers: List<Int>) : MapTile<Int>(markers.first()) {

    fun isHead() = markers.contains(0)

    fun isKnot(index: Int) = markers.contains(index)
}