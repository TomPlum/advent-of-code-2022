package io.github.tomplum.aoc.communication.packet

import io.github.tomplum.aoc.communication.packet.strategy.DataStreamMarkerFinderStrategy

/**
 * To fix the communication system, the [PacketInterceptor] adds a subroutine to the device that detects
 * a start-of-packet marker in the data-stream.
 *
 * In the protocol being used by the Elves, the start of a given sequence is indicated by a sequence
 * of n characters that are all different.
 *
 * @param dataStream A string of characters representing the data-stream buffer
 */
class PacketInterceptor(private val dataStream: String) {
    /**
     * Locates the position (1-based index) in which the marker
     * segment begins.
     *
     * @param strategy The strategy by which to search via
     * @return The position at which the marker begins in the [dataStream]
     */
    fun locateMarkerStartingLocation(strategy: DataStreamMarkerFinderStrategy): Int {
        return strategy.find(dataStream)
    }
}