package io.github.tomplum.aoc.assignment.strategy

import io.github.tomplum.aoc.assignment.SectionAssignment

class DuplicateAssignmentFinder : RedundantAssignmentFinderStrategy {
    override fun findRedundantAssignments(sectionAssignments: Pair<SectionAssignment, SectionAssignment>): Int {
        val (first, second) = sectionAssignments
        val secondWrapsFirst = first.startingId >= second.startingId && first.endingId <= second.endingId
        val firstWrapsSecond = second.startingId >= first.startingId && second.endingId <= first.endingId
        return if (secondWrapsFirst || firstWrapsSecond) 1 else 0
    }
}