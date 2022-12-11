package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day11Test {
    private val solution = Day11()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(111210)
    }

    @Test
    fun partTwo() {
        assertThat(solution.part2()).isEqualTo(15447387620)
    }
}