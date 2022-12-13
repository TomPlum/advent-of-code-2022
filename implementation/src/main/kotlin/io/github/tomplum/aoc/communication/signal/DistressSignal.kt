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

    fun findCorrectlyOrderedPacketPairs(): Int = pairs
        .filter { pair -> pair.isCorrectlyOrdered() }
        .sumOf { pair -> pair.index }

    fun findDecoderKey(): Int {
        val packets = (this.packets + listOf(firstDividerPacket, secondDividerPacket)).toMutableList()
        packets.sortWith { a, b -> compare(b.value, a.value) }
        return (packets.indexOf(firstDividerPacket) + 1) * (packets.indexOf(secondDividerPacket) + 1)
    }

    inner class PacketPair(val index: Int, private val first: Packet, private val second: Packet) {
        fun isCorrectlyOrdered(): Boolean {
            return compare(first.value, second.value) >= 1
        }
    }

    private fun compare(left: JsonNode, right: JsonNode): Int {
        if (left is IntNode && right is IntNode) {
            return right.intValue() - left.intValue()
        } else if (left is ArrayNode && right is ArrayNode) {
            val smallestListSize = listOf(left, right).minOf { it.size() } - 1
            (0..smallestListSize).forEach { i ->
                val result = compare(left[i], right[i])
                if (result != 0) {
                    return compare(left[i], right[i])
                }
            }

            return right.size() - left.size()
        } else if (left is IntNode && right is ArrayNode) {
            return compare(left.toArray(), right)
        } else if (left is ArrayNode && right is IntNode) {
            return compare(left, right.toArray())
        }

        throw IllegalArgumentException("Cannot compare $left and $right")
    }

    private fun getDividerPacketWithValue(value: Int): Packet {
        val data = objectMapper.createArrayNode()
        val leftInner = objectMapper.createArrayNode()
        leftInner.add(IntNode(value))
        data.add(leftInner)
        return Packet.fromDataStream(0, data)
    }

    private fun IntNode.toArray(): ArrayNode {
        val arrayNode = objectMapper.createArrayNode()
        arrayNode.add(this)
        return arrayNode
    }
}