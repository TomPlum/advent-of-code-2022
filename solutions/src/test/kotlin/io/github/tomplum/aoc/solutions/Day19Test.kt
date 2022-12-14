package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day19Test {
    private val solution = Day19()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(1365)
    }

    @Test
    fun partTwo() {
        assertThat(solution.part2()).isEqualTo(4864)
    }
}