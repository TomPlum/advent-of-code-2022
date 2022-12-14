package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.fuel.FuelConsole
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day25 : Solution<String, Int> {
    private val numbers = InputReader.read<String>(Day(25)).value
    private val console = FuelConsole(numbers)

    override fun part1(): String {
        return console.getConsoleInput()
    }

    override fun part2(): Int {
        return 49
    }
}