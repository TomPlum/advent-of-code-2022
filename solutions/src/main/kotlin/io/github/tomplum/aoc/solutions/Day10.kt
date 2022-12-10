package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.communication.cpu.ClockCircuit
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day10 : Solution<Int, Int> {

    private val program = InputReader.read<String>(Day(10)).value
    private val clockCircuit = ClockCircuit(program)

    override fun part1(): Int {
        return clockCircuit.run().calculateSignalStrengthSum(listOf(20, 60, 100, 140, 180, 220))
    }
}