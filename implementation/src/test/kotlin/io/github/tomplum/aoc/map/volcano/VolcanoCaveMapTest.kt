package io.github.tomplum.aoc.map.volcano

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class VolcanoCaveMapTest {
    private val scan = TestInputReader.read<String>("day16/example.txt").value
    private val map = VolcanoCaveMap(scan)

    @Test
    fun exampleOne() {
        assertThat(map.findMaximumFlowRate()).isEqualTo(1651)
    }
}