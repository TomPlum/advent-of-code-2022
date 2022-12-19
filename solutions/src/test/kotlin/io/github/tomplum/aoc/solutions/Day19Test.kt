package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day19Test {
    private val solution = Day19()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(2820) // 2820 too high, 741 too low
    }
}