package io.github.tomplum.aoc.communication.signal

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

private val objectMapper = jacksonObjectMapper()

/**
 * A state of comparison between two nodes of data
 * from a [Packet].
 *
 * @param left The left packet
 * @param right The right packet
 */
class PacketComparison(private val left: JsonNode, private val right: JsonNode) {
    operator fun component1() = left
    operator fun component2() = right

    companion object {
        /**
         * Constructs a [PacketComparison] instance from the
         * given [pair] of [Packet]s.
         *
         * @param pair The pair of packets
         * @return The constructed instance
         */
        fun fromPacketPair(pair: PacketPair): PacketComparison {
            return PacketComparison(pair.first.value, pair.second.value)
        }
    }

    val areBothIntegers get() = left is IntNode && right is IntNode
    val areBothLists get() = left is ArrayNode && right is ArrayNode

    val leftIsList get() = left is ArrayNode && right is IntNode
    val rightIsList get() = left is IntNode && right is ArrayNode

    val valueDifference get() = right.intValue() - left.intValue()
    val sizeDifference get() = right.size() - left.size()

    val range get() = (0 until listOf(left, right).minOf { list -> list.size() })

    /**
     * Returns a new [PacketComparison] instance
     * using the nested children at the given [index]
     * in both sides of the pair.
     *
     * @param index The target index
     * @return The new comparison
     */
    fun childAt(index: Int) = PacketComparison(left[index], right[index])

    /**
     * Normalises any [IntNode] values into an [ArrayNode].
     * @return The normalised comparison
     */
    fun toLists() = when {
        leftIsList -> PacketComparison(left, right.toArray())
        rightIsList -> PacketComparison(left.toArray(), right)
        else -> this
    }

    /**
     * Converts a [JsonNode] into
     * an [ArrayNode].
     * @return The converted node
     */
    private fun JsonNode.toArray(): ArrayNode {
        val arrayNode = objectMapper.createArrayNode()
        arrayNode.add(this)
        return arrayNode
    }
}