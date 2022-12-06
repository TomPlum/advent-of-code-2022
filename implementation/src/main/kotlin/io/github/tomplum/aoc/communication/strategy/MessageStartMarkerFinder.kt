package io.github.tomplum.aoc.communication.strategy

/**
 * A start-of-message marker is just like a start-of-packet marker,
 * except it consists of 14 distinct characters rather than 4.
 * @see [PacketStartMarkerFinder]
 */
class MessageStartMarkerFinder : DataStreamMarkerFinderStrategy() {
    override fun find(stream: String): Int {
        return super.findMarkerFirstInstancePosition(stream, 14)
    }
}