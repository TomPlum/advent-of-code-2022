package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.simluator.keepsafe.KeepAwaySimulator
import io.github.tomplum.aoc.simluator.keepsafe.monkey.MonkeyNoteParser
import io.github.tomplum.aoc.simluator.keepsafe.strategy.RidiculousWorryRelief
import io.github.tomplum.aoc.simluator.keepsafe.strategy.WorryRelief
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day11 : Solution<Long, Long> {

    private val notes = InputReader.read<String>(Day(11)).value
    private val monkeys = MonkeyNoteParser().parse(notes)
    private val simulator = KeepAwaySimulator(monkeys)

    override fun part1(): Long {
        val strategy = WorryRelief()
        return simulator.simulate(20, strategy)
    }

    override fun part2(): Long {
        val strategy = RidiculousWorryRelief(monkeys)
        return simulator.simulate(10_000, strategy)
    }
}