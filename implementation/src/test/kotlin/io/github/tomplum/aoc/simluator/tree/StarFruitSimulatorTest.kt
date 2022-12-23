package io.github.tomplum.aoc.simluator.tree

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class StarFruitSimulatorTest {
    private val scan = TestInputReader.read<String>("day23/example.txt").value
    private val simulator = StarFruitSimulator(scan)

    @Test
    fun examplePartOne() {
        assertThat(simulator.findElvenBoundingRectangle()).isEqualTo(110)
    }
}