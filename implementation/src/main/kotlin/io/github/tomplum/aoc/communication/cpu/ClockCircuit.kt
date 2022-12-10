package io.github.tomplum.aoc.communication.cpu

import io.github.tomplum.libs.logging.AdventLogger

class ClockCircuit(private val program: List<String>) {
    private val registerBuffer = Array(program.size * 2) { 0 }

    fun run(bufferIndexes: List<Int>): Int {
        var xRegister = 1
        var cycle = 0
        registerBuffer[0] = xRegister

        program.forEach { line ->
            val instruction = when(line.split(" ")[0]) {
                "noop" -> NoOp()
                "addx" -> Add.fromString(line)
                else -> throw IllegalArgumentException("Unknown program instruction [$line]")
            }

            val xCurrent = xRegister
            xRegister = instruction.execute(xRegister)
            if (instruction is Add)  {
                registerBuffer[cycle] = xCurrent
                registerBuffer[cycle + 1] = xRegister
                cycle += 2
            } else {
                registerBuffer[cycle] = xRegister
                cycle += 1
            }
        }
        // 1, 1, 4, 4, -1
        return bufferIndexes.sumOf { cycle ->
            AdventLogger.debug("Cycle $cycle: Signal Strength =  $cycle * ${registerBuffer[cycle - 1]}")
            registerBuffer[cycle - 1] * cycle
        }
    }
}