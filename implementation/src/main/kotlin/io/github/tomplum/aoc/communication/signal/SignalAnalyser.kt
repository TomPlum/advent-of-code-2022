package io.github.tomplum.aoc.communication.signal

/**
 * Analyses a [DistressSignal] to determine the
 * correctness of its packet order.
 *
 * @param signal The signal to analyse
 */
class SignalAnalyser(private val signal: DistressSignal) {

    /**
     * Locates all the packets that are in the correct
     * order before having been sorted.
     * @return The sum of the indices of all correctly ordered packets
     */
    fun findCorrectlyOrderedPacketPairs(): Int = signal.pairs
        .filter { pair -> PacketComparison.fromPacketPair(pair).compare() >= 1 }
        .sumOf { pair -> pair.index }

    /**
     * Calculates the decoder-key of the [signal].
     * The key is calculated by locating the two divider
     * packets in the data-stream, as per the distress
     * signal protocol.
     * @return The signal decoder key
     */
    fun findDecoderKey(): Int = signal.allPackets
        .sortedWith { a, b -> PacketComparison(b.value, a.value).compare() }
        .let { sorted ->
            val firstDividerIndex = sorted.indexOf(signal.firstDividerPacket) + 1
            val secondDividerIndex = sorted.indexOf(signal.secondDividerPacket) + 1
            firstDividerIndex * secondDividerIndex
        }

    /**
     * Compares two set of packet data from a [PacketComparison].
     *
     * Packet data consists of lists and integers.
     * Each list starts with [, ends with ], and contains zero
     * or more comma-separated values (either integers or other lists).
     * Each packet is always a list and appears on its own line.
     *
     * When comparing two values, the first value is called left and
     * the second value is called right. Then:
     *
     *  - If both values are integers, the lower integer should come
     *    first. If the left integer is lower than the right integer,
     *    the inputs are in the right order. If the left integer is
     *    higher than the right integer, the inputs are not in the
     *    right order. Otherwise, the inputs are the same integer;
     *    continue checking the next part of the input.
     *
     *  - If both values are lists, compare the first value of each
     *  list, then the second value, and so on. If the left list runs
     *  out of items first, the inputs are in the right order. If the
     *  right list runs out of items first, the inputs are not in the
     *  right order. If the lists are the same length and no comparison
     *  makes a decision about the order, continue checking the next
     *  part of the input.
     *
     *  - If exactly one value is an integer, convert the integer to
     *  a list which contains that integer as its only value, then
     *  retry the comparison. For example, if comparing [0,0,0] and
     *  2, convert the right value to [2] (a list containing 2); the
     *  result is then found by instead comparing [0,0,0] and [2].
     *
     *  This function returns a comparison value.
     *  - If the packet is in the correct order, the value will be >= 1
     *  - If the packet is not in the correct order, the value will be <= 0
     *
     *  @return the comparison value
     */
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