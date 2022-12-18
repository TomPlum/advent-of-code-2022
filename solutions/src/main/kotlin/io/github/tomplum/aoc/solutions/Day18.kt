package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.lava.LavaDroplet
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day18 : Solution<Int, Int> {
    private val scan = InputReader.read<String>(Day(18)).value
    private val lavaDroplet = LavaDroplet(scan)

    override fun part1(): Int {
        return lavaDroplet.getSurfaceArea()
    }

    override fun part2(): Int {
        return lavaDroplet.getExteriorSurfaceArea()
    }
}