package io.github.tomplum.aoc.communication.strategy

class MessageStartMarkerFinder : DataStreamMarkerFinderStrategy() {
    override fun find(stream: String): Int {
        return super.findMarkerFirstInstancePosition(stream, 14)
    }
}