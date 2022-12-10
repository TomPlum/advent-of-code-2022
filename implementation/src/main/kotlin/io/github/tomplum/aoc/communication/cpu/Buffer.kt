package io.github.tomplum.aoc.communication.cpu

import io.github.tomplum.libs.logging.AdventLogger

data class Buffer(val snapshots: List<RegisterSnapshot>) {
    fun calculateSignalStrengthSum(bufferIndexes: List<Int>) = bufferIndexes.sumOf { cycle ->
        AdventLogger.debug("Cycle $cycle: Signal Strength =  $cycle * ${snapshots[cycle - 1].xRegister}")
        snapshots[cycle - 1].xRegister * cycle
    }

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