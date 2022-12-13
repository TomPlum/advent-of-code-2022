package io.github.tomplum.aoc.communication.signal

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class DistressSignal(data: List<String>) {

    private val objectMapper = jacksonObjectMapper()
    private val firstDividerPacket = getDividerPacketWithValue(2)
    private val secondDividerPacket = getDividerPacketWithValue(6)

    private val packets = data.filter { line -> line.isNotBlank() }.mapIndexed { index, data ->
        Packet.fromDataStream(index + 1, objectMapper.readTree(data))
    }

    private val pairs = packets.chunked(2).mapIndexed { i, packets ->
        PacketPair(i + 1, packets[0], packets[1])
    }

    fun determinePacketPairOrdering(): Int {
        return pairs.filter { pair -> pair.inCorrectOrder() }.sumOf { pair -> pair.index }
    }

    fun findDecoderKey(): Int {
        val packets = (this.packets + listOf(firstDividerPacket, secondDividerPacket)).toMutableList()
        packets.sortWith { a, b -> compare(b.value, a.value) }
        return (packets.indexOf(firstDividerPacket) + 1) * (packets.indexOf(secondDividerPacket) + 1)
    }

    inner class PacketPair(val index: Int, private val first: Packet, private val second: Packet) {
        fun inCorrectOrder(): Boolean {
            return compare(first.value, second.value) == 1
        }
    }

    private fun compare(first: JsonNode, second: JsonNode): Int {
        if (first is IntNode && second is IntNode) {
            val leftInt = first.numberValue().toInt()
            val rightInt = second.numberValue().toInt()

            return if (leftInt < rightInt) {
                1
            } else if (leftInt > rightInt) {
                -1
            } else {
                0
            }
        } else if (first is ArrayNode && second is ArrayNode) {
            val smallestListSize = listOf(first, second).minOf { it.size() } - 1
            IntRange(0, smallestListSize).forEach { i ->
                val result = compare(first[i], second[i])
                if (result != 0) return compare(first[i], second[i])
            }

            return if (first.size() < second.size()) {
                1
            } else if (first.size() > second.size()) {
                -1
            } else {
                0
            }
        } else if (first is IntNode && second is ArrayNode) {
            val arrayNode = objectMapper.createArrayNode()
            arrayNode.add(first)
            return compare(arrayNode, second)
        } else if (first is ArrayNode && second is IntNode) {
            val arrayNode = objectMapper.createArrayNode()
            arrayNode.add(second)
            return compare(first, arrayNode)
        }

        throw IllegalArgumentException("Cannot compare $first and $second")
    }

    private fun getDividerPacketWithValue(value: Int): Packet {
        val data = objectMapper.createArrayNode()
        val leftInner = objectMapper.createArrayNode()
        leftInner.add(IntNode(value))
        data.add(leftInner)
        return Packet.fromDataStream(0, data)
    }
}