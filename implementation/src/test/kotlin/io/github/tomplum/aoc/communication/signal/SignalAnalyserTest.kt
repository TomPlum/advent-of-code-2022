package io.github.tomplum.aoc.communication.signal

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class SignalAnalyserTest {
    private val data = TestInputReader.read<String>("day13/example.txt").value
    private val distressSignal = DistressSignal(data)
    private val signalAnalyser = SignalAnalyser(distressSignal)

    @Test
    fun examplePartOne() {
        assertThat(signalAnalyser.findCorrectlyOrderedPacketPairs()).isEqualTo(13)
    }

    @Test
    fun examplePartTwo() {
        assertThat(signalAnalyser.findDecoderKey()).isEqualTo(140)
    }
}