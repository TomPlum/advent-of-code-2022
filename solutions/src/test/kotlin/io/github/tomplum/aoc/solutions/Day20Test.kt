package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day20Test {
    private val solution = Day20()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(3466)
    }

    @Test
    fun partTwo() {
        assertThat(solution.part2()).isEqualTo(9995532008348)
    }
}