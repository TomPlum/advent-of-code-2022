package io.github.tomplum.aoc.communication.cpu.instruction

import io.github.tomplum.aoc.communication.cpu.MemorySnapshot
import io.github.tomplum.aoc.communication.cpu.ClockCircuit

/**
 * A single executable instruction in a [ClockCircuit] program.
 */
interface Instruction {
    /**
     * The numerical value of the instruction.
     * @see Add for an example of how this is used
     */
    val value: Int

    /**
     * Executed the instruction against the [previous]
     * memory snapshot.
     *
     * @param previous The snapshot from the previous CPU cycle
     * @return An collection of memory snapshots for the current and future cycles
     */
    fun execute(previous: MemorySnapshot): List<MemorySnapshot>
}