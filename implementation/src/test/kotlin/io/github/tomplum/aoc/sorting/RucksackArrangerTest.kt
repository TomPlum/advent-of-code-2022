package io.github.tomplum.aoc.sorting

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class RucksackArrangerTest {
    @Test
    fun examplePartOne() {
        val rucksacks = TestInputReader.read<String>("/day3/example.txt").value
        val rucksackArranger = RucksackArranger(rucksacks)
        assertThat(rucksackArranger.getDuplicateItemPrioritySum()).isEqualTo(157)
    }
}