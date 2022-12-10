package io.github.tomplum.aoc.communication.cpu

import io.github.tomplum.libs.logging.AdventLogger

data class Buffer(val snapshots: List<RegisterSnapshot>) {
    fun calculateSignalStrengthSum(bufferIndexes: List<Int>) = bufferIndexes.sumOf { cycle ->
        AdventLogger.debug("Cycle $cycle: Signal Strength =  $cycle * ${snapshots[cycle - 1].xRegister}")
        snapshots[cycle - 1].xRegister * cycle
    }

    fun print() {
        val display = StringBuilder()

        snapshots.drop(1).chunked(40).forEach { line ->
            var spritePosition: Int
            line.forEach { snapshot ->
                val currentDrawingIndex = (snapshot.cycle % 40) - 1
                spritePosition = snapshot.xRegister
                val sprite = (spritePosition - 1)..(spritePosition + 1)
                if (currentDrawingIndex in sprite) {
                    display.append("#")
                } else {
                    display.append(".")
                }
            }
            display.append("\n")
        }
        AdventLogger.info(display)
    }
}