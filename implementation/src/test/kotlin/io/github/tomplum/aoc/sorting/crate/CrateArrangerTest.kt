package io.github.tomplum.aoc.sorting.crate

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import io.github.tomplum.aoc.sorting.crate.strategy.CrateMover9000
import io.github.tomplum.aoc.sorting.crate.strategy.CrateMover9001
import org.junit.jupiter.api.Test

class CrateArrangerTest {

    private val drawing = TestInputReader.read<String>("/day5/example.txt").value
    private val arranger = CrateArranger(drawing)

    @Test
    fun exampleOne() {
        val sortingStrategy = CrateMover9000()
        val topRowCrates = arranger.consolidate(sortingStrategy)
        assertThat(topRowCrates).isEqualTo("CMZ")
    }

    @Test
    fun exampleTwo() {
        val sortingStrategy = CrateMover9001()
        val topRowCrates = arranger.consolidate(sortingStrategy)
        assertThat(topRowCrates).isEqualTo("MCD")
    }
}