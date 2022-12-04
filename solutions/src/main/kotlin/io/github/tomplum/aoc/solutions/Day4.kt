package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.assignment.CleaningAssigner
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day4 : Solution<Int, Int> {
    private val sectionAssignments = InputReader.read<String>(Day(4)).value
    private val cleaningAssigner = CleaningAssigner(sectionAssignments)

    override fun part1(): Int {
        return cleaningAssigner.findDuplicateAssignmentPairs()
    }
}