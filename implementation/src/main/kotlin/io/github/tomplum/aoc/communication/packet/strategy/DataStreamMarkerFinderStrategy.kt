package io.github.tomplum.aoc.communication.packet.strategy

abstract class DataStreamMarkerFinderStrategy {
    /**
     * Performs the strategy-specific logic to locate
     * the marker in the given [stream].
     *
     * @param stream The data-stream buffer
     * @return The position of the marker
     */
    abstract fun find(stream: String): Int

    /**
     * Locates the starting position of the marker in the given [stream].
     * Searches based on the given [protocolLength].
     *
     * @param stream The data-stream buffer
     * @param protocolLength The length of the packet or message
     * @return The position of the marker
     */
    protected fun findMarkerFirstInstancePosition(stream: String, protocolLength: Int): Int {
        val segments = stream.toList().windowed(protocolLength)
        segments.forEach { chars ->
            if (!chars.containsDuplicates()) {
                return segments.indexOf(chars) + protocolLength
            }
        }
        throw IllegalArgumentException("Data Stream [$stream] does not contain a valid marker")
    }

    /**
     * Checks if the given list contains duplicates.
     * @return true if contains duplicates, else false
     */
    private fun List<Char>.containsDuplicates(): Boolean {
        return this.distinct().size != this.size
    }
}