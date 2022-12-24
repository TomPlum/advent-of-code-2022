package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.map.valley.ValleyMap
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day24 : Solution<Int, Int> {
    private val data = InputReader.read<String>(Day(24)).value
    private val map = ValleyMap(data)

    override fun part1(): Int {
        return map.traverseBlizzards()
    }

    override fun part2(): Int {
        return map.traverseValleyWithSnacks()
    }
}