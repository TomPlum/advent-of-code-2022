package io.github.tomplum.aoc.assignment

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.assignment.strategy.DuplicateAssignmentFinder
import io.github.tomplum.aoc.assignment.strategy.OverlappingAssignmentFinder
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class CleaningAssignerTest {

    private val sectionAssignments = TestInputReader.read<String>("/day4/example.txt").value
    private val assigner = CleaningAssigner(sectionAssignments)

    @Test
    fun exampleOne() {
        val strategy = DuplicateAssignmentFinder()
        val duplicateAssignmentPairs = assigner.countRedundantAssignments(strategy)
        assertThat(duplicateAssignmentPairs).isEqualTo(2)
    }

    @Test
    fun exampleTwo() {
        val strategy = OverlappingAssignmentFinder()
        val overlappingAssignmentPairs = assigner.countRedundantAssignments(strategy)
        assertThat(overlappingAssignmentPairs).isEqualTo(4)
    }
}