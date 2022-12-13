package io.github.tomplum.aoc.communication.signal

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class DistressSignalTest {
    private val data = TestInputReader.read<String>("day13/example.txt").value
    private val distressSignal = DistressSignal(data)

    @Test
    fun examplePartOne() {
        assertThat(distressSignal.determinePacketPairOrdering()).isEqualTo(13)
    }
}