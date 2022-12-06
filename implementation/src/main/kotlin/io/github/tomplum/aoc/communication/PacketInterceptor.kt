package io.github.tomplum.aoc.communication

import io.github.tomplum.aoc.communication.strategy.DataStreamMarkerFinderStrategy

class PacketInterceptor(private val dataStream: String) {
    fun locateMarkerStartingLocation(strategy: DataStreamMarkerFinderStrategy): Int {
        return strategy.find(dataStream)
    }
}