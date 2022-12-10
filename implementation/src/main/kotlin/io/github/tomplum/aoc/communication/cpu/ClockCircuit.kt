package io.github.tomplum.aoc.communication.cpu

import io.github.tomplum.aoc.communication.cpu.instruction.InstructionParser

class ClockCircuit(private val program: List<String>) {

    private val parser = InstructionParser()

    fun run() = parser.parse(program).fold(listOf(RegisterSnapshot.initial())) { buffer, instruction ->
        buffer + instruction.execute(buffer.last())
    }.let { Buffer(it) }
}