package io.github.tomplum.aoc.fuel

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SNAFUConverterTest {
    private val input = TestInputReader.read<String>("day25/example.txt").value
    private val converter = SNAFUConverter(input)

    @Test
    fun examplePartOne() {
        assertThat(converter.getConsoleInput()).isEqualTo("2=-1=0")
    }
}