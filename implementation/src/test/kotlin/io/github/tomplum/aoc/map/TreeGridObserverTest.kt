package io.github.tomplum.aoc.map

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class TreeGridObserverTest {
    private val heightMapData = TestInputReader.read<String>("day8/example.txt").value
    private val observer = TreeGridObserver(heightMapData)

    @Test
    fun examplePartOne() {
        assertThat(observer.countTreesVisibleFromOutside()).isEqualTo(21)
    }
}