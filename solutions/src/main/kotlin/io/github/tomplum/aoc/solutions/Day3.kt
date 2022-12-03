package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.sorting.RucksackArranger
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day3 : Solution<Int, Int> {
    private val rucksacks = InputReader.read<String>(Day(3)).value
    private val arranger = RucksackArranger(rucksacks)

    override fun part1(): Int {
        return arranger.getDuplicateItemPrioritySum()
    }

    override fun part2(): Int {
        return arranger.getGroupCommonItemsPrioritySum()
    }
}