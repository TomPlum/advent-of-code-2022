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
        packets.sortWith { a, b -> Pair(b.value, a.value).compare() }
        return (packets.indexOf(firstDividerPacket) + 1) * (packets.indexOf(secondDividerPacket) + 1)
    }

    inner class Pair(private val left: JsonNode, private val right: JsonNode) {
        val areBothIntegers get() = left is IntNode && right is IntNode
        val areBothLists get() = left is ArrayNode && right is ArrayNode

        val leftIsList get() = left is ArrayNode && right is IntNode
        val rightIsList get() = left is IntNode && right is ArrayNode

        val valueDifference get() = right.intValue() - left.intValue()
        val sizeDifference get() = right.size() - left.size()

        val range get() = (0 until listOf(left, right).minOf { list -> list.size() })

        fun childAt(index: Int) = Pair(left[index], right[index])

        fun toLists() = when {
            leftIsList -> Pair(left, right.toArray())
            rightIsList -> Pair(left.toArray(), right)
            else -> this
        }

        operator fun component1() = left
        operator fun component2() = right
    }

    private fun Pair.compare(): Int {
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

    private fun getDividerPacketWithValue(value: Int): Packet {
        val data = objectMapper.createArrayNode()
        val leftInner = objectMapper.createArrayNode()
        leftInner.add(IntNode(value))
        data.add(leftInner)
        return Packet.fromDataStream(0, data)
    }

    private fun JsonNode.toArray(): ArrayNode {
        val arrayNode = objectMapper.createArrayNode()
        arrayNode.add(this)
        return arrayNode
    }

    private fun PacketPair.isCorrectlyOrdered(): Boolean {
        return Pair(first.value, second.value).compare() >= 1
    }
}