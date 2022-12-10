package io.github.tomplum.aoc.communication.cpu

import io.github.tomplum.aoc.communication.cpu.instruction.Add
import io.github.tomplum.aoc.communication.cpu.instruction.NoOp

class ClockCircuit(private val program: List<String>) {
    fun run(): Buffer {
        val instructions = program.map { line ->
            when (line.split(" ")[0]) {
                "noop" -> NoOp()
                "addx" -> Add.fromString(line)
                else -> throw IllegalArgumentException("Unknown program instruction [$line]")
            }
        }

        val initialSnapshot = RegisterSnapshot(1, 1)
        val bufferSnapshots = instructions.fold(listOf(initialSnapshot)) { snapshots, instruction ->
            snapshots + instruction.execute(snapshots.last())
        }

        return Buffer(bufferSnapshots)
    }
}