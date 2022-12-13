package io.github.tomplum.aoc.communication.signal

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

private val objectMapper = jacksonObjectMapper()

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