package io.github.tomplum.aoc.assignment

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class CleaningAssignerTest {
    @Test
    fun exampleOne() {
        val sectionAssignments = TestInputReader.read<String>("/day4/example.txt").value
        val assigner = CleaningAssigner(sectionAssignments)
        assertThat(assigner.findDuplicateAssignmentPairs()).isEqualTo(2)
    }
}