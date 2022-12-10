package io.github.tomplum.aoc.communication.cpu.instruction

import io.github.tomplum.aoc.communication.cpu.MemorySnapshot

/**
 * This instruction takes 2 CPU cycles to complete.
 * - On the first cycle, it does no mutation - the register value is copied.
 * - On the second cycle, it adds the given instruction [value] from the last cycle.
 *
 * @param value The value in which to add at the end of the second cycle
 */
data class Add(override val value: Int) : Instruction {

    companion object {
        fun fromString(string: String): Add {
            val valueString = string.removePrefix("addx ").trim()
            if (valueString.startsWith("-")) {
                return Add(-valueString.removePrefix("-").toInt())
            }
            return Add(valueString.toInt())
        }
    }


    override fun execute(previous: MemorySnapshot): List<MemorySnapshot> {
        return listOf(
            MemorySnapshot(previous.xRegister, previous.cycle + 1),
            MemorySnapshot(previous.xRegister + value, previous.cycle + 2)
        )
    }
}