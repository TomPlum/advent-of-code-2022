package io.github.tomplum.aoc.simluator.monkey

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class KeepAwaySimulatorTest {
    private val notes = TestInputReader.read<String>("day11/example.txt").value
    private val simulator = KeepAwaySimulator(notes)

    @Test
    fun examplePartOne() {
        assertThat(simulator.simulate(20)).isEqualTo(10605)
    }

    @Test
    fun examplePartTwo() {
        assertThat(simulator.simulate(10000)).isEqualTo(2713310158)
    }
}