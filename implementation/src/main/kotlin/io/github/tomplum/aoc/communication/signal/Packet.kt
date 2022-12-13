package io.github.tomplum.aoc.communication.signal

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode

data class Packet private constructor(val index: Int, val value: ArrayNode) {
    companion object {
        fun fromDataStream(index: Int, data: JsonNode): Packet {
            if (data.isArray) {
                return Packet(index, data as ArrayNode)
            } else {
                throw IllegalArgumentException("Cannot create packet from non-array node")
            }
        }
    }
}