package io.github.tomplum.aoc.communication.signal

/**
 * A pair of packets from a [DistressSignal] data stream.
 *
 * @param index The position of the pair in the stream
 * @param first The first, left packet in the pair
 * @param second The second, right packet in the pair
 */
data class PacketPair(val index: Int, val first: Packet, val second: Packet)