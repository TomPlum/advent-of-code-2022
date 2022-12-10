package io.github.tomplum.aoc.communication.cpu.instruction

import io.github.tomplum.aoc.communication.cpu.RegisterSnapshot

interface Instruction {
    val value: Int
    fun execute(buffer: RegisterSnapshot): List<RegisterSnapshot>
}