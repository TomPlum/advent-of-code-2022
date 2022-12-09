package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day9Test {
    private val solution = Day9()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(6081)
    }

    @Test
    fun partTwo() {
        assertThat(solution.part2()).isEqualTo(2487)
    }
}