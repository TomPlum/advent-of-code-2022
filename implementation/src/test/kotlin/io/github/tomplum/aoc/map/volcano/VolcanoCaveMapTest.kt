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

    @Test
    fun exampleOneOld() {
        val map = OldVolcanoMap(scan)
        assertThat(map.findMaximumFlowRate()).isEqualTo(1651)
    }

    @Test
    fun comparison() {
        val old = OldVolcanoMap(scan)
        old.findMaximumFlowRate()
        val new = VolcanoCaveMap(scan)
        new.findMaximumFlowRate()
        val oldTimes = old.valveTimes
        val newTimes = new.times

        oldTimes.forEachIndexed { index, old ->
            old.entries.forEachIndexed { i, (oldLabel, oldTime) ->
                val newMappings = newTimes[index]
                val newTime = newMappings[Valve("$oldLabel$oldLabel")]
                if (oldTime != newTime) {
                    throw IllegalArgumentException("Time disparity!")
                }
            }
        }

        assertThat(map.findMaximumFlowRate()).isEqualTo(1651)
    }
}