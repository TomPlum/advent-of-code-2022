package io.github.tomplum.aoc.communication.cpu

import io.github.tomplum.libs.logging.AdventLogger

/**
 * An exported list of [snapshots] taken from the memory buffer of a [ClockCircuit].
 * @param snapshots The full history of memory snapshots for every cycle.
 */
data class Buffer(val snapshots: List<MemorySnapshot>) {
    /**
     * Calculates the signal strength at each of the
     * given [cycles] and returns the total sum.
     *
     * @param cycles A list of target CPU cycles
     * @return The sum of the signal strengths at the given [cycles]
     */
    fun calculateSignalStrengthSum(cycles: List<Int>) = cycles.sumOf { cycle ->
        snapshots[cycle - 1].xRegister * cycle
    }

    /**
     * Uses the data from the list of [snapshots] to determine
     * which pixels on the 40 x 6 CRT screen to illuminate.
     */
    fun print() = snapshots.chunked(40).fold(StringBuilder()) { display, line ->
        var spritePosition: Int
        line.forEach { snapshot ->
            val currentDrawingIndex = (snapshot.cycle % 40) - 1
            spritePosition = snapshot.xRegister
            val sprite = (spritePosition - 1)..(spritePosition + 1)
            display.append(if (currentDrawingIndex in sprite) "#" else ".")
        }
        display.append("\n")
        display
    }.also { display -> AdventLogger.info(display) }
}