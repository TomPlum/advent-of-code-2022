package io.github.tomplum.aoc.communication.signal

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

private val objectMapper = jacksonObjectMapper()

class PacketComparison(private val left: JsonNode, private val right: JsonNode) {
    operator fun component1() = left
    operator fun component2() = right

    companion object {
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

    fun childAt(index: Int) = PacketComparison(left[index], right[index])

    fun toLists() = when {
        leftIsList -> PacketComparison(left, right.toArray())
        rightIsList -> PacketComparison(left.toArray(), right)
        else -> this
    }

    private fun JsonNode.toArray(): ArrayNode {
        val arrayNode = objectMapper.createArrayNode()
        arrayNode.add(this)
        return arrayNode
    }
}