package io.github.tomplum.aoc.communication.strategy

class PacketStartMarkerFinder : DataStreamMarkerFinderStrategy() {
    override fun find(stream: String): Int {
        return super.findMarkerFirstInstancePosition(stream, 4)
    }
}