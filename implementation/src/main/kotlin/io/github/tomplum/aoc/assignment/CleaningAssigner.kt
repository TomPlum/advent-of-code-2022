package io.github.tomplum.aoc.assignment

import io.github.tomplum.aoc.assignment.strategy.RedundantAssignmentFinderStrategy

class CleaningAssigner(private val sectionAssignments: List<String>) {
    fun countRedundantAssignments(strategy: RedundantAssignmentFinderStrategy): Int = sectionAssignments.sumOf { section ->
        val split = section.split(",")
        val first = split[0].split("-").toList().map { it.toInt() }
        val second = split[1].split("-").toList().map { it.toInt() }
        val firstAssignment = SectionAssignment(first[0], first[1])
        val secondAssignment = SectionAssignment(second[0], second[1])
        strategy.findRedundantAssignments(Pair(firstAssignment, secondAssignment))
    }
}