package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.communication.packet.PacketInterceptor
import io.github.tomplum.aoc.communication.packet.strategy.MessageStartMarkerFinder
import io.github.tomplum.aoc.communication.packet.strategy.PacketStartMarkerFinder
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day6 : Solution<Int, Int> {
    private val dataStream = InputReader.read<String>(Day(6)).asSingleString()
    private val packetInterceptor = PacketInterceptor(dataStream)

    override fun part1(): Int {
        val strategy = PacketStartMarkerFinder()
        return packetInterceptor.locateMarkerStartingLocation(strategy)
    }

    override fun part2(): Int {
        val strategy = MessageStartMarkerFinder()
        return packetInterceptor.locateMarkerStartingLocation(strategy)
    }
}