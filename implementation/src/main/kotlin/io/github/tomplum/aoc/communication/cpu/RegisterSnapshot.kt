package io.github.tomplum.aoc.communication.cpu

data class RegisterSnapshot(val instruction: Instruction, val xRegister: Int, val cycle: Int)