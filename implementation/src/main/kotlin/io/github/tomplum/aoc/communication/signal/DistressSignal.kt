package io.github.tomplum.aoc.communication.signal

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class DistressSignal(data: List<String>) {

    private val objectMapper = jacksonObjectMapper()
    private val firstDividerPacket = Packet.divider(2)
    private val secondDividerPacket = Packet.divider(6)

    private val packets = data.filter { line -> line.isNotBlank() }.mapIndexed { index, data ->
        Packet.fromDataStream(index + 1, objectMapper.readTree(data))
    }

    private val pairs = packets.chunked(2).mapIndexed { i, packets ->
        PacketPair(i + 1, packets[0], packets[1])
    }

    fun findCorrectlyOrderedPacketPairs(): Int = pairs
        .filter { pair -> pair.isCorrectlyOrdered() }
        .sumOf { pair -> pair.index }

    fun findDecoderKey(): Int {
        val packets = (this.packets + listOf(firstDividerPacket, secondDividerPacket)).toMutableList()
        packets.sortWith { a, b -> PacketComparison(b.value, a.value).compare() }

        val firstDividerIndex = packets.indexOf(firstDividerPacket) + 1
        val secondDividerIndex = packets.indexOf(secondDividerPacket) + 1
        return firstDividerIndex * secondDividerIndex
    }

    private fun PacketComparison.compare(): Int {
        when {
            areBothIntegers -> return valueDifference
            areBothLists -> {
                range.forEach { i ->
                    val childPair = childAt(i)
                    val result = childPair.compare()
                    if (result != 0) {
                        return childPair.compare()
                    }
                }

                return sizeDifference
            }
            rightIsList || leftIsList -> return toLists().compare()
            else -> throw IllegalArgumentException("Cannot compare $this")
        }
    }

    private fun PacketPair.isCorrectlyOrdered(): Boolean {
        return PacketComparison(first.value, second.value).compare() >= 1
    }
}