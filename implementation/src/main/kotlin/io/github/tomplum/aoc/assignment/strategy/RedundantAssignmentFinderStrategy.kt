package io.github.tomplum.aoc.assignment.strategy

import io.github.tomplum.aoc.assignment.SectionAssignment

interface RedundantAssignmentFinderStrategy {
    fun findRedundantAssignments(sectionAssignments: Pair<SectionAssignment, SectionAssignment>): Int
}