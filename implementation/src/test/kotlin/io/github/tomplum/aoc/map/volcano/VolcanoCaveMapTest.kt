package io.github.tomplum.aoc.map.volcano

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class VolcanoCaveMapTest {
    private val scan = TestInputReader.read<String>("day16/example.txt").value
    private val volcano = VolcanoCaveMap(scan)

    @Test
    fun examplePartOne() {
        assertThat(volcano.findMaximumReleasablePressure()).isEqualTo(1651)
    }

    @Test
    fun examplePartTwo() {
        assertThat(volcano.findMaximumReleasablePressureWithElephant()).isEqualTo(1707)
    }

    @Test
    fun exampleOneOld() {
        val map = OldVolcanoMap(scan)
        assertThat(map.findMaximumFlowRate()).isEqualTo(1651)
    }
}