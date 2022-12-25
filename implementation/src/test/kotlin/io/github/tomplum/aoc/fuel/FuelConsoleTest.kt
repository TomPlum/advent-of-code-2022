package io.github.tomplum.aoc.fuel

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class FuelConsoleTest {
    private val input = TestInputReader.read<String>("day25/example.txt").value
    private val console = FuelConsole(input)

    @Test
    fun examplePartOne() {
        assertThat(console.getConsoleInput()).isEqualTo("2=-1=0")
    }
}