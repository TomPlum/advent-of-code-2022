package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.simluator.monkey.KeepAwaySimulator
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day11 : Solution<Long, Long> {

    private val notes = InputReader.read<String>(Day(11)).value
    private val simulator = KeepAwaySimulator(notes)

    override fun part1(): Long {
        return simulator.simulate(20)
    }

    override fun part2(): Long {
        return simulator.simulate(10_000)
    }
}