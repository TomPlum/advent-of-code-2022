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
        assertThat(simulator.simulate()).isEqualTo(3068)
    }
}