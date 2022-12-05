package io.github.tomplum.aoc.sorting.crate

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class CrateArrangerTest {
    @Test
    fun exampleOne() {
        val drawing = TestInputReader.read<String>("/day5/example.txt").value
        val arranger = CrateArranger(drawing)
        assertThat(arranger.consolidate()).isEqualTo("CMZ")
    }
}