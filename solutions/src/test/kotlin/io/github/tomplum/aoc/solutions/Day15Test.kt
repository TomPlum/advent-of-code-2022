package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day15Test {
    private val solution = Day15()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(5564017)
    }

    @Test
    fun partTwo() {
        assertThat(solution.part2()).isEqualTo(11558423398893)
    }
}