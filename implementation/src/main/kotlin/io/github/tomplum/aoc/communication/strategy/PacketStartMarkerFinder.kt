package io.github.tomplum.aoc.communication.strategy

/**
 * To fix the communication system, you need to add a subroutine to the device
 * that detects a start-of-packet marker in the data-stream. In the protocol
 * being used by the Elves, the start of a packet is indicated by a sequence
 * of four characters that are all different.
 */
class PacketStartMarkerFinder : DataStreamMarkerFinderStrategy() {
    override fun find(stream: String): Int {
        return super.findMarkerFirstInstancePosition(stream, 4)
    }
}