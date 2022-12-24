package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day22Test {
    private val solution = Day22()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(97356)
    }

    @Test
    fun partTwo() {
        assertThat(solution.part2()).isEqualTo(120175)
    }
}