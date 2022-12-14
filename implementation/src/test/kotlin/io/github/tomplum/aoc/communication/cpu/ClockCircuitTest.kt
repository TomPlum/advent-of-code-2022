package io.github.tomplum.aoc.communication.cpu

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class ClockCircuitTest {

    @Test
    fun examplePartOneLarge() {
        val program = TestInputReader.read<String>("/day10/example-large.txt").value
        val clockCircuit = ClockCircuit(program)
        val signalStrengthSum = clockCircuit.run().calculateSignalStrengthSum(listOf(20, 60, 100, 140, 180, 220))
        assertThat(signalStrengthSum).isEqualTo(13140)
    }

    @Test
    fun examplePartTwo() {
        val program = TestInputReader.read<String>("/day10/example-large.txt").value
        val clockCircuit = ClockCircuit(program)
        clockCircuit.run().print()
    }
}