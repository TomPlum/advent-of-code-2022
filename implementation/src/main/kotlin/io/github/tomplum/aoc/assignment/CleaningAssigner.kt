package io.github.tomplum.aoc.assignment

class CleaningAssigner(private val sectionAssignments: List<String>) {
    fun findDuplicateAssignmentPairs(): Int = sectionAssignments.sumOf { section ->
        val split = section.split(",")
        val first = split[0].split("-").toList().map { it.toInt() }
        val second = split[1].split("-").toList().map { it.toInt() }
        if (first[0] >= second[0] && first[1] <= second[1] || second[0] >= first[0] && second[1] <= first[1]) {
            1.toInt()
        } else {
            0
        }
    }

    fun findOverlappingAssignmentPairs(): Int = sectionAssignments.sumOf { section ->
        val split = section.split(",")
        val first = split[0].split("-").toList().map { it.toInt() }
        val second = split[1].split("-").toList().map { it.toInt() }
        val firstRange = IntRange(first[0], first[1])
        val secondRange = IntRange(second[0], second[1])
        if (first[0] < second[0]) {
            val overlaps = firstRange.find { secondRange.contains(it) }
            if (overlaps != null) 1 else (0.toInt())
        } else {
            val overlaps = secondRange.find { firstRange.contains(it) }
            if (overlaps != null) 1 else 0
        }
    }
}