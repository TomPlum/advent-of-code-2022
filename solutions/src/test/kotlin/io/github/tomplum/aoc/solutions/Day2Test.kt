package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day2Test {
    private val solution = Day2()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(11449)
    }
}