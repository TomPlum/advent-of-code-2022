package io.github.tomplum.aoc.map.hill

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class HillHeightMapTest {
    private val data = TestInputReader.read<String>("day12/example.txt").value
    private val heightMap = HillHeightMap(data)

    @Test
    fun examplePartOne() {
        assertThat(heightMap.findShortestRouteToBestSignal()).isEqualTo(31)
    }
}