package io.github.tomplum.aoc.simluator.tree

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class StarFruitSimulatorTest {


    @Test
    fun examplePartOne() {
        val scan = TestInputReader.read<String>("day23/example.txt").value
        val simulator = StarFruitSimulator(scan)
        assertThat(simulator.findElvenBoundingRectangle()).isEqualTo(110)
    }

    @Test
    fun examplePartOneSmall() {
        val scan = TestInputReader.read<String>("day23/small-example.txt").value
        val simulator = StarFruitSimulator(scan)
        assertThat(simulator.findElvenBoundingRectangle(5)).isEqualTo(110)
    }

    @Test
    fun examplePartTwo() {
        val scan = TestInputReader.read<String>("day23/example.txt").value
        val simulator = StarFruitSimulator(scan)
        assertThat(simulator.findRoundWithNoMovement()).isEqualTo(20)
    }
}