package io.github.tomplum.aoc.map.pond

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class PondMapTest {
    @Test
    fun examplePartOneSmall() {
        val scan = TestInputReader.read<String>("day18/example-small.txt").value
        val pondMap = PondMap(scan)
        assertThat(pondMap.getLavaDropletSurfaceArea()).isEqualTo(10)
    }

    @Test
    fun examplePartOneLarger() {
        val scan = TestInputReader.read<String>("day18/example-large.txt").value
        val pondMap = PondMap(scan)
        assertThat(pondMap.getLavaDropletSurfaceArea()).isEqualTo(64)
    }
}