package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day17Test {
    private val solution = Day17()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(3239)
    }

    @Test
    fun partTwo() {
        assertThat(solution.part2()).isEqualTo(1594842406882)
    }
}