package io.github.tomplum.aoc.communication.cpu.instruction

import io.github.tomplum.aoc.communication.cpu.RegisterSnapshot

data class NoOp(override val value: Int = 0) : Instruction {
    override fun execute(previous: RegisterSnapshot): List<RegisterSnapshot> {
        return listOf(RegisterSnapshot(previous.xRegister, previous.cycle + 1))
    }
}