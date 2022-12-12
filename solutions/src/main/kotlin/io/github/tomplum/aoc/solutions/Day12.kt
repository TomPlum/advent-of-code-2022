package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.hill.HillHeightMap
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day12 : Solution<Int, Int> {

    private val data = InputReader.read<String>(Day(12)).value
    private val hillHeightMap = HillHeightMap(data)

    override fun part1(): Int {
        return hillHeightMap.findShortestRouteToBestSignal()
    }

    override fun part2(): Int {
        return hillHeightMap.findShortestRouteFromLowestElevationToBestSignal()
    }
}