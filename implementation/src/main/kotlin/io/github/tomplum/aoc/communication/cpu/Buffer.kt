package io.github.tomplum.aoc.communication.cpu

import io.github.tomplum.libs.logging.AdventLogger

/**
 * An exported list of [snapshots] taken from the register
 * buffer of a [ClockCircuit].
 * @param snapshots The full history of memory snapshots for every cycle.
 */
data class Buffer(val snapshots: List<RegisterSnapshot>) {
    fun calculateSignalStrengthSum(bufferIndexes: List<Int>) = bufferIndexes.sumOf { cycle ->
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