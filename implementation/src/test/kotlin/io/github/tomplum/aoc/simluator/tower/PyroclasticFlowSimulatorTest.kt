package io.github.tomplum.aoc.simluator.tower

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class PyroclasticFlowSimulatorTest {
    private val data = TestInputReader.read<String>("day17/example.txt").asSingleString()
    private val simulator = PyroclasticFlowSimulator(data)

    @Test
    fun examplePartOne() {
        assertThat(simulator.simulate(2022)).isEqualTo(3068)
    }

    @Test
    fun examplePartTwo() {
        assertThat(simulator.simulate(1_000_000_000_000)).isEqualTo(1_514_285_714_288)
    }
}