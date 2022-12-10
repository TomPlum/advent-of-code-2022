package io.github.tomplum.aoc.communication.cpu.instruction

import io.github.tomplum.aoc.communication.cpu.RegisterSnapshot

/**
 * This instruction takes 1 CPU cycle to execute.
 * It does not mutate a register value.
 */
class NoOp : Instruction {
    override val value: Int = 0

    override fun execute(previous: RegisterSnapshot): List<RegisterSnapshot> {
        return listOf(RegisterSnapshot(previous.xRegister, previous.cycle + 1))
    }
}