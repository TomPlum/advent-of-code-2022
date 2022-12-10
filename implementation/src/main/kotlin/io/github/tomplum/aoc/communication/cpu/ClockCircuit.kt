package io.github.tomplum.aoc.communication.cpu

import io.github.tomplum.aoc.communication.cpu.instruction.InstructionParser

/**
 * A component in the communication device that drives
 * the CPU and the CRT screen.
 *
 * @param program A list of executable instructions
 */
class ClockCircuit(private val program: List<String>) {

    private val parser = InstructionParser()

    /**
     * Runs the [program], executing each of the instructions
     * and taking snapshots of the current state of memory each
     * cycle.
     *
     * @return An exported collection of snapshots from the memory buffer
     */
    fun run() = parser.parse(program).fold(listOf(RegisterSnapshot.initial())) { buffer, instruction ->
        buffer + instruction.execute(buffer.last())
    }.let { snapshots -> Buffer(snapshots) }
}