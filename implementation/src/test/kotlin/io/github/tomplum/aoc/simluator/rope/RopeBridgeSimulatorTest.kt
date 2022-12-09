package io.github.tomplum.aoc.simluator.rope

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class RopeBridgeSimulatorTest {

    @Test
    fun exampleOnePartOne() {
        val directions = TestInputReader.read<String>("day9/example.txt").value
        val simulator = RopeBridgeSimulator(directions)
        assertThat(simulator.countUniquePositionsVisited()).isEqualTo(13)
    }

    @Test
    fun exampleOnePartTwo() {
        val directions = TestInputReader.read<String>("day9/example.txt").value
        val simulator = RopeBridgeSimulator(directions)
        assertThat(simulator.countUniquePositionsVisited(9)).isEqualTo(1)
    }

    @Test
    fun exampleTwoPartTwo() {
        val directions = TestInputReader.read<String>("day9/example-large.txt").value
        val simulator = RopeBridgeSimulator(directions)
        assertThat(simulator.countUniquePositionsVisited(9)).isEqualTo(36)
    }
}