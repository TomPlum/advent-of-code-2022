package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day10Test {
    private val solution = Day10()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(13480)
    }

    @Test
    fun partTwo() {
        assertThat(solution.part2()).isEqualTo("EGJBGCFK")
    }
}