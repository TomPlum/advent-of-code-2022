package io.github.tomplum.aoc.communication

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.communication.strategy.MessageStartMarkerFinder
import io.github.tomplum.aoc.communication.strategy.PacketStartMarkerFinder
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PacketInterceptorTest {
    @Nested
    inner class PacketStart {

        private val strategy = PacketStartMarkerFinder()

        @Test
        fun exampleOne() {
            val dataStream = TestInputReader.read<String>("/day6/example1.txt").asSingleString()
            val packetInterceptor = PacketInterceptor(dataStream)
            assertThat(packetInterceptor.locateMarkerStartingLocation(strategy)).isEqualTo(7)
        }

        @Test
        fun exampleTwo() {
            val dataStream = TestInputReader.read<String>("/day6/example2.txt").asSingleString()
            val packetInterceptor = PacketInterceptor(dataStream)
            assertThat(packetInterceptor.locateMarkerStartingLocation(strategy)).isEqualTo(5)
        }

        @Test
        fun exampleThree() {
            val dataStream = TestInputReader.read<String>("/day6/example3.txt").asSingleString()
            val packetInterceptor = PacketInterceptor(dataStream)
            assertThat(packetInterceptor.locateMarkerStartingLocation(strategy)).isEqualTo(6)
        }

        @Test
        fun exampleFour() {
            val dataStream = TestInputReader.read<String>("/day6/example4.txt").asSingleString()
            val packetInterceptor = PacketInterceptor(dataStream)
            assertThat(packetInterceptor.locateMarkerStartingLocation(strategy)).isEqualTo(10)
        }

        @Test
        fun exampleFive() {
            val dataStream = TestInputReader.read<String>("/day6/example5.txt").asSingleString()
            val packetInterceptor = PacketInterceptor(dataStream)
            assertThat(packetInterceptor.locateMarkerStartingLocation(strategy)).isEqualTo(11)
        }
    }

    @Nested
    inner class MessageStart {

        private val strategy = MessageStartMarkerFinder()

        @Test
        fun exampleOne() {
            val packetInterceptor = PacketInterceptor("mjqjpqmgbljsphdztnvjfqwrcgsmlb")
            assertThat(packetInterceptor.locateMarkerStartingLocation(strategy)).isEqualTo(19)
        }

        @Test
        fun exampleTwo() {
            val packetInterceptor = PacketInterceptor("bvwbjplbgvbhsrlpgdmjqwftvncz")
            assertThat(packetInterceptor.locateMarkerStartingLocation(strategy)).isEqualTo(23)
        }

        @Test
        fun exampleThree() {
            val packetInterceptor = PacketInterceptor("nppdvjthqldpwncqszvftbrmjlhg")
            assertThat(packetInterceptor.locateMarkerStartingLocation(strategy)).isEqualTo(23)
        }

        @Test
        fun exampleFour() {
            val packetInterceptor = PacketInterceptor("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")
            assertThat(packetInterceptor.locateMarkerStartingLocation(strategy)).isEqualTo(29)
        }

        @Test
        fun exampleFive() {
            val packetInterceptor = PacketInterceptor("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")
            assertThat(packetInterceptor.locateMarkerStartingLocation(strategy)).isEqualTo(26)
        }
    }
}