package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day23Test {
    private val solution = Day23()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(4302)
    }

    @Test
    fun partTwo() {
        assertThat(solution.part2()).isEqualTo(1025)
    }
}