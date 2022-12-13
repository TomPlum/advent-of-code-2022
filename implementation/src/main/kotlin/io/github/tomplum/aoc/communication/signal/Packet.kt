package io.github.tomplum.aoc.communication.signal

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

private val objectMapper = jacksonObjectMapper()

class Packet private constructor(val index: Int, val value: ArrayNode) {
    companion object {
        fun fromDataStream(index: Int, data: JsonNode): Packet {
            if (data.isArray) {
                return Packet(index, data as ArrayNode)
            } else {
                throw IllegalArgumentException("Cannot create packet from non-array node")
            }
        }

        fun divider(value: Int): Packet {
            val data = objectMapper.createArrayNode()
            val leftInner = objectMapper.createArrayNode()
            leftInner.add(IntNode(value))
            data.add(leftInner)
            return Packet.fromDataStream(0, data)
        }
    }
}