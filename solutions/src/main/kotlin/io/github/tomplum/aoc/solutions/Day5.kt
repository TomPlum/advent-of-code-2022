package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.sorting.crate.CrateArranger
import io.github.tomplum.aoc.sorting.crate.strategy.CrateMover9000
import io.github.tomplum.aoc.sorting.crate.strategy.CrateMover9001
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day5 : Solution<String, String> {

    private val crateStackDrawing = InputReader.read<String>(Day(5)).value
    private val arranger = CrateArranger(crateStackDrawing)

    override fun part1(): String {
        val sortingStrategy = CrateMover9000()
        return arranger.consolidate(sortingStrategy)
    }

    override fun part2(): String {
        val sortingStrategy = CrateMover9001()
        return arranger.consolidate(sortingStrategy)
    }
}