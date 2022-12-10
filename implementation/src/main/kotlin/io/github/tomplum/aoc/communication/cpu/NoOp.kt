package io.github.tomplum.aoc.communication.cpu

class NoOp : Instruction {
    override fun execute(value: Int): Int {
        return value
    }
}