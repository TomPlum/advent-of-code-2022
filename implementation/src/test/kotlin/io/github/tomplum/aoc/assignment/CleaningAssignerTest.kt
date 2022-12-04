package io.github.tomplum.aoc.assignment

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class CleaningAssignerTest {

    private val sectionAssignments = TestInputReader.read<String>("/day4/example.txt").value
    private val assigner = CleaningAssigner(sectionAssignments)

    @Test
    fun exampleOne() {
        val duplicateAssignmentPairs = assigner.findDuplicateAssignmentPairs()
        assertThat(duplicateAssignmentPairs).isEqualTo(2)
    }

    @Test
    fun exampleTwo() {
        val overlappingAssignmentPairs = assigner.findOverlappingAssignmentPairs()
        assertThat(overlappingAssignmentPairs).isEqualTo(4)
    }
}