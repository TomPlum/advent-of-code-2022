package io.github.tomplum.aoc.communication.cpu

import io.github.tomplum.libs.logging.AdventLogger

data class Buffer(val snapshots: List<RegisterSnapshot>) {
    fun calculateSignalStrengthSum(bufferIndexes: List<Int>) = bufferIndexes.sumOf { cycle ->
        AdventLogger.debug("Cycle $cycle: Signal Strength =  $cycle * ${snapshots[cycle - 1].value}")
        snapshots[cycle - 1].value * cycle
    }
}