package io.github.tomplum.aoc.communication.cpu.instruction

class InstructionParser {
    fun parse(program: List<String>): List<Instruction> = program.map { line ->
        when (line.split(" ")[0]) {
            "noop" -> NoOp()
            "addx" -> Add.fromString(line)
            else -> throw IllegalArgumentException("Unknown program instruction [$line]")
        }
    }
}