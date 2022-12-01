package io.github.tomplum.aoc

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class CalorieCounterTest {
    @Test
    fun exampleOne() {
        val input = TestInputReader.read<String>("day1/example.txt").value
        val calorieCounter = CalorieCounter(input)
        assertThat(calorieCounter.getHighestCalorieCount()).isEqualTo(24000)
    }
}