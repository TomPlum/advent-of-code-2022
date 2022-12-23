package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.simluator.tree.StarFruitSimulator
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day23 : Solution<Int, Int> {
    private val notes = InputReader.read<String>(Day(23)).value
    private val simulator = StarFruitSimulator(notes)

    override fun part1(): Int {
        return simulator.findElvenBoundingRectangle()
    }

    override fun part2(): Int {
        return simulator.findRoundWithNoMovement()
    }
}