package io.github.tomplum.aoc.map.lava

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class LavaDropletTest {
    @Test
    fun examplePartOneSmall() {
        val scan = TestInputReader.read<String>("day18/example-small.txt").value
        val lavaDroplet = LavaDroplet(scan)
        assertThat(lavaDroplet.getSurfaceArea()).isEqualTo(10)
    }

    @Test
    fun examplePartOneLarger() {
        val scan = TestInputReader.read<String>("day18/example-large.txt").value
        val lavaDroplet = LavaDroplet(scan)
        assertThat(lavaDroplet.getSurfaceArea()).isEqualTo(64)
    }

    @Test
    fun examplePartTwoLarger() {
        val scan = TestInputReader.read<String>("day18/example-large.txt").value
        val lavaDroplet = LavaDroplet(scan)
        assertThat(lavaDroplet.getExteriorSurfaceArea()).isEqualTo(58)
    }
}