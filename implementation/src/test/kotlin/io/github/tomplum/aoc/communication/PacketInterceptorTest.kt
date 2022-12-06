package io.github.tomplum.aoc.communication

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class PacketInterceptorTest {
    @Test
    fun exampleOne() {
        val dataStream = TestInputReader.read<String>("/day6/example1.txt").asSingleString()
        val packetInterceptor = PacketInterceptor(dataStream)
        assertThat(packetInterceptor.findStartOfPacketMarker()).isEqualTo(7)
    }

    @Test
    fun exampleTwo() {
        val dataStream = TestInputReader.read<String>("/day6/example2.txt").asSingleString()
        val packetInterceptor = PacketInterceptor(dataStream)
        assertThat(packetInterceptor.findStartOfPacketMarker()).isEqualTo(5)
    }

    @Test
    fun exampleThree() {
        val dataStream = TestInputReader.read<String>("/day6/example3.txt").asSingleString()
        val packetInterceptor = PacketInterceptor(dataStream)
        assertThat(packetInterceptor.findStartOfPacketMarker()).isEqualTo(6)
    }

    @Test
    fun exampleFour() {
        val dataStream = TestInputReader.read<String>("/day6/example4.txt").asSingleString()
        val packetInterceptor = PacketInterceptor(dataStream)
        assertThat(packetInterceptor.findStartOfPacketMarker()).isEqualTo(10)
    }

    @Test
    fun exampleFive() {
        val dataStream = TestInputReader.read<String>("/day6/example5.txt").asSingleString()
        val packetInterceptor = PacketInterceptor(dataStream)
        assertThat(packetInterceptor.findStartOfPacketMarker()).isEqualTo(11)
    }
}