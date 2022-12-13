package io.github.tomplum.aoc.communication.signal

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

private val objectMapper = jacksonObjectMapper()

/**
 * You climb the hill and again try contacting the Elves.
 * However, you instead receive a signal you weren't expecting: a [DistressSignal].
 *
 * Your handheld device must still not be working properly; the packets
 * from the distress signal got decoded out of order. This class
 * re-orders the list of received packets to decode the message.
 *
 * @param data A list of received packet data
 */
class DistressSignal(data: List<String>) {

    val firstDividerPacket = Packet.divider(2)
    val secondDividerPacket = Packet.divider(6)

    private val packets = data.filter { line -> line.isNotBlank() }.mapIndexed { index, data ->
        Packet.fromDataStream(index + 1, objectMapper.readTree(data))
    }

    val allPackets = (packets + listOf(firstDividerPacket, secondDividerPacket)).toMutableList()

    val pairs = packets.chunked(2).mapIndexed { i, packets ->
        PacketPair(i + 1, packets[0], packets[1])
    }
}