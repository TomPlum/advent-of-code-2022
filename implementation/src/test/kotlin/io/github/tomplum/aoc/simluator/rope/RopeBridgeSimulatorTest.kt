package io.github.tomplum.aoc.simluator.rope

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class RopeBridgeSimulatorTest {

    private val directions = TestInputReader.read<String>("day9/example.txt").value
    private val simulator = RopeBridgeSimulator(directions)

    @Test
    fun examplePartOne() {
        assertThat(simulator.countUniquePositionsVisited()).isEqualTo(13)
    }
}