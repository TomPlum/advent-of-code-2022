package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.simluator.robot.OreCollectingRobotSimulator
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day19 : Solution<Int, Int> {
    private val blueprints = InputReader.read<String>(Day(19)).value
    private val robotSimulator = OreCollectingRobotSimulator(blueprints)

    override fun part1(): Int {
        return robotSimulator.simulate()
    }

    override fun part2(): Int {
        return robotSimulator.simulateWhileElephantsAreEating()
    }
}