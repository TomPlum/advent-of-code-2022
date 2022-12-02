package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.game.RockPaperScissors
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day2 : Solution<Int, Int> {

    private val strategyGuide = InputReader.read<String>(Day(2)).value
    private val rockPaperScissors = RockPaperScissors(strategyGuide)

    override fun part1(): Int? {
        return rockPaperScissors.play()
    }

    override fun part2(): Int? {
        return rockPaperScissors.playNewRules()
    }
}