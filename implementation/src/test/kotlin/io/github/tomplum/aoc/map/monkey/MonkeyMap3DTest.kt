package io.github.tomplum.aoc.map.monkey

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class MonkeyMap3DTest {
    private val notes = TestInputReader.read<String>("day22/example.txt").value
    private val map = MonkeyMap3D(notes)

    @Test
    fun examplePartTwo() {
        assertThat(map.findFinalPassword()).isEqualTo(3051)
    }
}