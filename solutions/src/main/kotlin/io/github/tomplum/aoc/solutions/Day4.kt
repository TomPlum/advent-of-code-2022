package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.assignment.CleaningAssigner
import io.github.tomplum.aoc.assignment.strategy.DuplicateAssignmentFinder
import io.github.tomplum.aoc.assignment.strategy.OverlappingAssignmentFinder
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day4 : Solution<Int, Int> {
    private val sectionAssignments = InputReader.read<String>(Day(4)).value
    private val cleaningAssigner = CleaningAssigner(sectionAssignments)

    override fun part1(): Int {
        val duplicateStrategy = DuplicateAssignmentFinder()
        return cleaningAssigner.countRedundantAssignments(duplicateStrategy)
    }

    override fun part2(): Int {
        val overlappingStrategy = OverlappingAssignmentFinder()
        return cleaningAssigner.countRedundantAssignments(overlappingStrategy)
    }
}