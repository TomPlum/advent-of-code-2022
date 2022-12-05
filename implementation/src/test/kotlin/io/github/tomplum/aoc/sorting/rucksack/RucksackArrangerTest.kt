package io.github.tomplum.aoc.sorting.rucksack

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import io.github.tomplum.aoc.sorting.rucksack.CommonCompartment
import io.github.tomplum.aoc.sorting.rucksack.GroupBadgePriority
import io.github.tomplum.aoc.sorting.rucksack.RucksackArranger
import org.junit.jupiter.api.Test

class RucksackArrangerTest {

    private val rucksacks = TestInputReader.read<String>("/day3/example.txt").value
    private val rucksackArranger = RucksackArranger(rucksacks)

    @Test
    fun examplePartOne() {
        val strategy = CommonCompartment()
        val prioritySum = rucksackArranger.calculateItemPrioritySum(strategy)
        assertThat(prioritySum).isEqualTo(157)
    }

    @Test
    fun examplePartTwo() {
        val strategy = GroupBadgePriority()
        val prioritySum = rucksackArranger.calculateItemPrioritySum(strategy)
        assertThat(prioritySum).isEqualTo(70)
    }
}