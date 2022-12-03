package io.github.tomplum.aoc.solutions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class Day3Test {
    private val solution = Day3()

    @Test
    fun partOne() {
        assertThat(solution.part1()).isEqualTo(7581)
    }
}