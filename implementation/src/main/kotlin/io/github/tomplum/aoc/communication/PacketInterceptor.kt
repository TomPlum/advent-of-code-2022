package io.github.tomplum.aoc.communication

class PacketInterceptor(private val datastream: String) {
    fun findStartOfPacketMarker(): Int {
        val segments = datastream.toList().windowed(4)
        segments.forEachIndexed { i, chars -> if (!chars.containsDuplicates()) return segments.indexOf(chars) + 4 }
        throw IllegalArgumentException("Datastream [$datastream] does not contain a start-of-packet marker")
    }

    private fun List<Char>.containsDuplicates(): Boolean {
        return this.distinct().size != this.size
    }
}