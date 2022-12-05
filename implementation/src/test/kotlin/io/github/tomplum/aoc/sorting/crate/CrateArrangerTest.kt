package io.github.tomplum.aoc.sorting.crate

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class CrateArrangerTest {

    private val drawing = TestInputReader.read<String>("/day5/example.txt").value
    private val arranger = CrateArranger(drawing)

    @Test
    fun exampleOne() {
        val topRowCrates = arranger.consolidate()
        assertThat(topRowCrates).isEqualTo("CMZ")
    }

    @Test
    fun exampleTwo() {
        val topRowCrates = arranger.consolidate9001()
        assertThat(topRowCrates).isEqualTo("MCD")
    }
}