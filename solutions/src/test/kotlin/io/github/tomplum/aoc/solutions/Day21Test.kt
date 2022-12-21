package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day21Test {
    private val solution = Day21()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(155708040358220)
    }

    @Test
    fun partTwo() {
        assertThat(solution.part2()).isEqualTo(3342154812537)
    }
}