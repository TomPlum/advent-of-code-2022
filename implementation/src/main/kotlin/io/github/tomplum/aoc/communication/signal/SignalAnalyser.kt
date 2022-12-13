package io.github.tomplum.aoc.communication.signal

class SignalAnalyser(private val signal: DistressSignal) {

    fun findCorrectlyOrderedPacketPairs(): Int = signal.pairs
        .filter { pair -> PacketComparison.fromPacketPair(pair).compare() >= 1 }
        .sumOf { pair -> pair.index }

    fun findDecoderKey(): Int = signal.allPackets
        .sortedWith { a, b -> PacketComparison(b.value, a.value).compare() }
        .let { sorted ->
            val firstDividerIndex = sorted.indexOf(signal.firstDividerPacket) + 1
            val secondDividerIndex = sorted.indexOf(signal.secondDividerPacket) + 1
            firstDividerIndex * secondDividerIndex
        }

    private fun PacketComparison.compare(): Int {
        when {
            areBothIntegers -> return valueDifference
            areBothLists -> {
                range.forEach { i ->
                    val childPair = childAt(i)
                    val result = childPair.compare()
                    if (result != 0) {
                        return childPair.compare()
                    }
                }

                return sizeDifference
            }

            rightIsList || leftIsList -> return toLists().compare()
            else -> throw IllegalArgumentException("Cannot compare $this")
        }
    }
}