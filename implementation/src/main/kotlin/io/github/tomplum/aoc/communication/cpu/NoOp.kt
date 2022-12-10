package io.github.tomplum.aoc.communication.cpu

data class NoOp(override val value: Int = 0) : Instruction {
    override fun execute(previous: RegisterSnapshot): List<RegisterSnapshot> {
        return listOf(RegisterSnapshot(previous.xRegister, previous.cycle + 1))
    }
}