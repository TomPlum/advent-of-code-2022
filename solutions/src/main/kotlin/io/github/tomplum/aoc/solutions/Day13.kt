package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.communication.signal.DistressSignal
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day13 : Solution<Int, Int> {
    private val data = InputReader.read<String>(Day(13)).value
    private val distressSignal = DistressSignal(data)

    override fun part1(): Int {
        return distressSignal.findCorrectlyOrderedPacketPairs()
    }

    override fun part2(): Int {
        return distressSignal.findDecoderKey()
    }
}