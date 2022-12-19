package io.github.tomplum.aoc.simluator.robot

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class OreCollectingRobotSimulatorTest {
    private val blueprints = TestInputReader.read<String>("day19/example.txt").value
    private val simulator = OreCollectingRobotSimulator(blueprints)

    @Test
    fun examplePartOne() {
        assertThat(simulator.simulate()).isEqualTo(33)
    }

    @Test
    fun examplePartTwo() {
        assertThat(simulator.simulateWhileElephantsAreEating()).isEqualTo(62)
    }
}