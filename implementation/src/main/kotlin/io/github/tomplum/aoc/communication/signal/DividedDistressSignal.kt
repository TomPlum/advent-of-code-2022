package io.github.tomplum.aoc.communication.signal

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.tomplum.extension.splitNewLine
import io.github.tomplum.libs.logging.AdventLogger

class DividedDistressSignal(data: List<String>) {

    private val objectMapper = jacksonObjectMapper()

    private val firstDividerPacket = getDividerPacketWithValue(2)
    private val secondDividerPacket = getDividerPacketWithValue(6)

    private val packets = data.splitNewLine().map { pair ->
        pair.map { list -> objectMapper.readTree(list) } as List<ArrayNode>
    }.flatten().toMutableList().let { packets ->
        packets.add(firstDividerPacket)
        packets.add(secondDividerPacket)
        packets
    }

    fun findDecoderKey(): Int {
        packets.sortWith { a, b -> compare(b, a) }

        return (packets.indexOf(firstDividerPacket) + 1) * (packets.indexOf(secondDividerPacket) + 1)
    }

    private fun getDividerPacketWithValue(value: Int): ArrayNode {
        val packet = objectMapper.createArrayNode()
        val leftInner = objectMapper.createArrayNode()
        leftInner.add(IntNode(value))
        packet.add(leftInner)
        return packet
    }

    private fun compare(first: JsonNode, second: JsonNode): Int {
        AdventLogger.debug("- Compare $first vs $second")
        if (first is IntNode && second is IntNode) {
            val leftInt = first.numberValue().toInt()
            val rightInt = second.numberValue().toInt()

            return if (leftInt < rightInt) {
                AdventLogger.debug("- Left side is smaller, so inputs are in the right order")
                1
            } else if (leftInt > rightInt) {
                AdventLogger.debug("- Right side is smaller, so inputs are in the wrong order")
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
                AdventLogger.debug("- Left side ran out of items, so inputs are in the right order")
                1
            } else if (first.size() > second.size()) {
                AdventLogger.debug("- Right side ran out of items, so inputs are in the wrong order")
                -1
            } else {
                0
            }
        } else if (first is IntNode && second is ArrayNode) {
            val arrayNode = objectMapper.createArrayNode()
            arrayNode.add(first)
            AdventLogger.debug("- Mixed types; convert left to $arrayNode and retry comparison")
            return compare(arrayNode, second)
        } else if (first is ArrayNode && second is IntNode) {
            val arrayNode = objectMapper.createArrayNode()
            arrayNode.add(second)
            AdventLogger.debug("- Mixed types; convert right to $arrayNode and retry comparison")
            return compare(first, arrayNode)
        }

        throw IllegalArgumentException("Cannot compare $first and $second")
    }
}