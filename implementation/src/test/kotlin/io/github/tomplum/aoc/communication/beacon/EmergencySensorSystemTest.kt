package io.github.tomplum.aoc.communication.beacon

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class EmergencySensorSystemTest {
    private val data = TestInputReader.read<String>("day15/example.txt").value
    private val emergencySensorSystem = EmergencySensorSystem(data)

    @Test
    fun examplePartOne() {
        assertThat(emergencySensorSystem.calculateBeaconExclusionZoneCount(10)).isEqualTo(26)
    }

    @Test
    fun examplePartTwo() {
        assertThat(emergencySensorSystem.locateDistressBeacon()).isEqualTo(56000011)
    }
}