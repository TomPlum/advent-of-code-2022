package io.github.tomplum.aoc.communication.cpu

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class ClockCircuitTest {


    @Test
    fun examplePartOneSmall() {
        val program = TestInputReader.read<String>("/day10/example.txt").value
        val clockCircuit = ClockCircuit(program)
        assertThat(clockCircuit.run(listOf(20, 60, 100, 140, 180, 220))).isEqualTo(13140)
    }

    @Test
    fun examplePartOneLarge() {
        val program = TestInputReader.read<String>("/day10/example-large.txt").value
        val clockCircuit = ClockCircuit(program)
        assertThat(clockCircuit.run(listOf(20, 60, 100, 140, 180, 220))).isEqualTo(13140)
    }
}