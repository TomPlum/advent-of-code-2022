package io.github.tomplum.aoc.assignment

import io.github.tomplum.aoc.assignment.strategy.RedundantAssignmentFinderStrategy

class CleaningAssigner(private val sectionAssignments: List<String>) {
    fun countRedundantAssignments(strategy: RedundantAssignmentFinderStrategy): Int = sectionAssignments.sumOf { section ->
        val values = section.split(",")
        val firstAssignment = SectionAssignment.fromString(values[0])
        val secondAssignment = SectionAssignment.fromString(values[1])
        val assignmentPair = Pair(firstAssignment, secondAssignment)
        strategy.findRedundantAssignments(assignmentPair)
    }
}