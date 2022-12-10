package io.github.tomplum.aoc.communication.cpu

import io.github.tomplum.libs.logging.AdventLogger
import java.util.Stack

class ClockCircuit(private val program: List<String>) {
    private val registerBuffer = Array(program.size * 2) { 0 }

    fun run(bufferIndexes: List<Int>): Int {
        var xRegister = 1
        var cycle = 0
        registerBuffer[0] = xRegister
        var inAddInstruction = false


        val instructions = program.map { line ->
            when (line.split(" ")[0]) {
                "noop" -> NoOp()
                "addx" -> Add.fromString(line)
                else -> throw IllegalArgumentException("Unknown program instruction [$line]")
            }
        }

        val bufferSnapshots = instructions.fold(listOf(RegisterSnapshot(1, 1))) { snapshots, instruction ->
            snapshots + instruction.execute(snapshots.last())
        }

        // 1, 1, 4, 4, -1
        // 13760 is too high for part one
        return bufferIndexes.sumOf { cycle ->
            AdventLogger.debug("Cycle $cycle: Signal Strength =  $cycle * ${bufferSnapshots[cycle - 1].value}")
            bufferSnapshots[cycle - 1].value * cycle
        }
    }
}