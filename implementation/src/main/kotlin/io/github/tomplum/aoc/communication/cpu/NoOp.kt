package io.github.tomplum.aoc.communication.cpu

class NoOp : Instruction {
    override fun execute(previous: RegisterSnapshot): List<RegisterSnapshot> {
        return listOf(RegisterSnapshot(previous.value, previous.cycle + 1))
    }
}