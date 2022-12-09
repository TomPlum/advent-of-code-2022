package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.simluator.rope.RopeBridgeSimulator
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day9 : Solution<Int, Int> {

    private val directions = InputReader.read<String>(Day(9)).value
    private val ropeBridgeSimulator = RopeBridgeSimulator(directions)

    override fun part1(): Int {
        return ropeBridgeSimulator.countUniquePositionsVisited()
    }

    override fun part2(): Int {
        return ropeBridgeSimulator.countForNKnots(9)
    }
}