package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.sand.RegolithReservoir
import io.github.tomplum.aoc.map.sand.ReservoirSimulator
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day14 : Solution<Int, Int> {
    private val scan = InputReader.read<String>(Day(14)).value
    private val reservoir = RegolithReservoir(scan)
    private val simulator = ReservoirSimulator(reservoir)

    override fun part1(): Int {
        return simulator.simulate()
    }

    override fun part2(): Int {
        return simulator.findSafeFloorSpace()
    }
}