package io.github.tomplum.aoc.communication.signal

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

private val objectMapper = jacksonObjectMapper()

/**
 * A singular packet of data in a [DistressSignal].
 *
 * @param index The one-based index of the packet
 * @param value The value of the packet
 */
class Packet private constructor(private val index: Int, val value: ArrayNode) {
    companion object {
        /**
         * Constructs a new [Packet] instance with the
         * given [index] and [data].
         *
         * @param index The index of the packet in the stream
         * @param data The node holding the data
         * @throws IllegalArgumentException if the data node is not an array
         * @return The constructed packet instance
         */
        fun fromDataStream(index: Int, data: JsonNode): Packet {
            if (data.isArray) {
                return Packet(index, data as ArrayNode)
            } else {
                throw IllegalArgumentException("Cannot create packet from non-array node")
            }
        }

        /**
         * Constructs a new divider [Packet].
         *
         * The [DistressSignal] protocol also requires
         * that two additional divider packets are included
         * in the data steam.
         *
         * The structure of the packet is [[[value]]]
         *
         * @param value The value of the packet
         * @return The constructed divider packet instance
         */
        fun divider(value: Int): Packet {
            val data = objectMapper.createArrayNode()
            val leftInner = objectMapper.createArrayNode()
            leftInner.add(IntNode(value))
            data.add(leftInner)
            return fromDataStream(0, data)
        }
    }
}