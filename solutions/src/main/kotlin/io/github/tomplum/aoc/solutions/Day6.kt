package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.communication.PacketInterceptor
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day6 : Solution<Int, Int> {
    private val dataStream = InputReader.read<String>(Day(6)).asSingleString()
    private val packetInterceptor = PacketInterceptor(dataStream)

    override fun part1(): Int {
        return packetInterceptor.findStartOfPacketMarker()
    }
}