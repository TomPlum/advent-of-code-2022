package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.volcano.OldVolcanoMap
import io.github.tomplum.aoc.map.volcano.VolcanoCaveMap
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day16 : Solution<Int, Int> {
    private val scan = InputReader.read<String>(Day(16)).value
    private val volcanoCaveMap = VolcanoCaveMap(scan)

    override fun part1(): Int {
        val oldMap = OldVolcanoMap(scan)
        return volcanoCaveMap.findMaximumFlowRate()
    }
}