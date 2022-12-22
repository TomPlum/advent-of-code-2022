package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.monkey.MonkeyMap
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day22 : Solution<Int, Int> {
    private val notes = InputReader.read<String>(Day(22)).value
    private val map = MonkeyMap(notes)

    override fun part1(): Int {
        return map.findFinalPassword()
    }
}