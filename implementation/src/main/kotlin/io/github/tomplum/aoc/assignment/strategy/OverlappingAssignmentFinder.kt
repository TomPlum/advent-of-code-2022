package io.github.tomplum.aoc.assignment.strategy

import io.github.tomplum.aoc.assignment.SectionAssignment

class OverlappingAssignmentFinder : RedundantAssignmentFinderStrategy {
    override fun findRedundantAssignments(sectionAssignments: Pair<SectionAssignment, SectionAssignment>): Int {
        val (first, second) = sectionAssignments
        val firstRange = IntRange(first.startingId, first.endingId)
        val secondRange = IntRange(second.startingId, second.endingId)
        return if (first.startingId < second.startingId) {
            val overlaps = firstRange.find { secondRange.contains(it) }
            if (overlaps != null) 1 else 0
        } else {
            val overlaps = secondRange.find { firstRange.contains(it) }
            if (overlaps != null) 1 else 0
        }
    }
}