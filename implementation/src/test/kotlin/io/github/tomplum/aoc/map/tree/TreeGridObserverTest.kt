package io.github.tomplum.aoc.map.tree

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import io.github.tomplum.aoc.map.tree.TreeGridObserver
import org.junit.jupiter.api.Test

class TreeGridObserverTest {
    private val heightMapData = TestInputReader.read<String>("day8/example.txt").value
    private val observer = TreeGridObserver(heightMapData)

    @Test
    fun examplePartOne() {
        assertThat(observer.countTreesVisibleFromOutside()).isEqualTo(21)
    }

    @Test
    fun examplePartTwo() {
        assertThat(observer.findHighestPossibleScenicScore()).isEqualTo(8)
    }
}