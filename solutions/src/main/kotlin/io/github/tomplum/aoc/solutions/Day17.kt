package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.simluator.tower.PyroclasticFlowSimulator
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution


class Day17 : Solution<Long, Int> {

    private val scan = InputReader.read<String>(Day(17)).asSingleString()
    private val pyroclasticFlowSimulator = PyroclasticFlowSimulator(scan)

    override fun part1(): Long {
        return pyroclasticFlowSimulator.simulate(2022)
    }
}