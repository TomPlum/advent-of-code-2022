package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.game.riddle.MonkeyRiddle
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day21: Solution<Long, Long> {

    private val monkeys = InputReader.read<String>(Day(21)).value
    private val riddle = MonkeyRiddle(monkeys)

    override fun part1(): Long {
        return riddle.solve()
    }
}