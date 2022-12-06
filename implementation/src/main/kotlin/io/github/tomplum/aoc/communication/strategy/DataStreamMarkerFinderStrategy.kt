package io.github.tomplum.aoc.communication.strategy

abstract class DataStreamMarkerFinderStrategy {

    abstract fun find(stream: String): Int

    protected fun findMarkerFirstInstancePosition(stream: String, protocolLength: Int): Int {
        val segments = stream.toList().windowed(protocolLength)
        segments.forEach { chars ->
            if (!chars.containsDuplicates()) {
                return segments.indexOf(chars) + protocolLength
            }
        }
        throw IllegalArgumentException("Data Stream [$stream] does not contain a valid marker")
    }

    private fun List<Char>.containsDuplicates(): Boolean {
        return this.distinct().size != this.size
    }
}