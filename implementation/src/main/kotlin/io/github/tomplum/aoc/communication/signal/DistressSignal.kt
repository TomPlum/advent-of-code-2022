package io.github.tomplum.aoc.communication.signal

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.tomplum.extension.splitNewLine
import io.github.tomplum.libs.logging.AdventLogger

class DistressSignal(data: List<String>) {

    private val objectMapper = jacksonObjectMapper()

    private val packetPairs = data.splitNewLine().mapIndexed { i, pair ->
        val nodes = pair.map { list ->
            objectMapper.readTree(list)
        } as List<ArrayNode>
        PacketPair(i + 1, nodes[0], nodes[1])
    }

    fun determinePacketPairOrdering(): Int {
        return packetPairs.filter { pair -> pair.inCorrectOrder() }.sumOf { pair -> pair.index }
    }

    inner class PacketPair(val index: Int, private val first: ArrayNode, private val second: ArrayNode) {
        fun inCorrectOrder(): Boolean {
            return compare(first, second) == -1
        }

        private fun compare(first: JsonNode, second: JsonNode): Int {
            AdventLogger.debug("- Compare $first vs $second")
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
                AdventLogger.debug("- Mixed types; convert right to $arrayNode and retry comparison")
                return compare(first, arrayNode)
            }

            throw IllegalArgumentException("Cannot compare $first and $second")
        }
    }
}