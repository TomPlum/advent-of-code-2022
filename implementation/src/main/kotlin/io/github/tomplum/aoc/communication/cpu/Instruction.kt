package io.github.tomplum.aoc.communication.cpu

interface Instruction {
    val value: Int
    fun execute(buffer: RegisterSnapshot): List<RegisterSnapshot>
}