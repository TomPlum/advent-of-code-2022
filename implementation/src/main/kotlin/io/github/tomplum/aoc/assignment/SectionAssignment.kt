package io.github.tomplum.aoc.assignment

data class SectionAssignment(val startingId: Int, val endingId: Int) {
    companion object {
        fun fromString(range: String): SectionAssignment {
            val values = range.split("-").toList().map { value -> value.toInt() }
            return SectionAssignment(values[0], values[1])
        }
    }
}