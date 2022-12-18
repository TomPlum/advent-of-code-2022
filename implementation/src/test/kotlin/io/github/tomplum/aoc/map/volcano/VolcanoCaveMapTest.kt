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
        assertThat(map.findMaximumReleasablePressure()).isEqualTo(1651)
    }

    @Test
    fun exampleOneOld() {
        val map = OldVolcanoMap(scan)
        assertThat(map.findMaximumFlowRate()).isEqualTo(1651)
    }

    @Test
    fun comparison() {
//        val old = OldVolcanoMap(scan)
//        old.findMaximumFlowRate()
//
//        val new = VolcanoCaveMap(scan)
//        new.findMaximumFlowRate()
//
//        val oldDistances = old.distances
//        val newDistances = new.distances
//
//        oldDistances.forEachIndexed { index, values ->
//            values.forEachIndexed { id, oldDistance ->
//                val newDistance = newDistances[index][id]
//                if (newDistance != oldDistance) {
//                    val oops = ""
//                }
//            }
//        }
//
//
//        val oldTimes = old.valveTimes
//        val newTimes = new.times
//
//        oldTimes.forEachIndexed { index, old ->
//            old.entries.forEachIndexed { i, (oldLabel, oldTime) ->
//                val newMappings = newTimes[index]
//                val newTime = newMappings[Valve(oldLabel)]
//                if (oldTime != newTime) {
//                    throw IllegalArgumentException("Time disparity!")
//                }
//            }
//        }
//
//        assertThat(map.findMaximumFlowRate()).isEqualTo(1651)
    }
}