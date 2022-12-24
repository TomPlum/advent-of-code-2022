package io.github.tomplum.aoc.map.valley

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class ValleyMapTest {
    private val data = TestInputReader.read<String>("day24/example.txt").value
    private val map = ValleyMap(data)

    @Test
    fun examplePartOne() {
        assertThat(map.traverseBlizzards()).isEqualTo(18)
    }

    @Test
    fun examplePartTwo() {
        assertThat(map.traverseValleyWithSnacks()).isEqualTo(54)
    }
}