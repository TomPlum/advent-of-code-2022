package io.github.tomplum.aoc.communication.cpu.instruction

import io.github.tomplum.aoc.communication.cpu.ClockCircuit

class InstructionParser {
    /**
     * Parses the given [program] lines and
     * produces a collection of executed instructions
     * for a [ClockCircuit]
     *
     * @param program A list of program lines
     * @throws IllegalArgumentException if a line contains an unknown command
     * @return A list of instructions
     */
    fun parse(program: List<String>): List<Instruction> = program.map { line ->
        when (line.split(" ")[0]) {
            "noop" -> NoOp()
            "addx" -> Add.fromString(line)
            else -> throw IllegalArgumentException("Unknown program instruction [$line]")
        }
    }
}