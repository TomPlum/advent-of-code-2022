package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.TreeGridObserver
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day8 : Solution<Int, Int> {
    private val heightMapData = InputReader.read<String>(Day(8)).value
    private val treeGridObserver = TreeGridObserver(heightMapData)

    override fun part1(): Int {
        return treeGridObserver.countTreesVisibleFromOutside()
    }
}