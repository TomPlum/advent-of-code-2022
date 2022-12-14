package io.github.tomplum.aoc.map.sand

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test


class ReservoirSimulatorTest {
    private val scan = TestInputReader.read<String>("day14/example.txt").value
    private val reservoir = RegolithReservoir(scan)
    private val simulator = ReservoirSimulator(reservoir)

    @Test
    fun examplePartOne() {
        assertThat(simulator.simulate()).isEqualTo(24)
    }
}