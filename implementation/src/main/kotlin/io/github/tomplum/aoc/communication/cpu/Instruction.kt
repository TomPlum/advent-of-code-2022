package io.github.tomplum.aoc.communication.cpu

interface Instruction {
    fun execute(buffer: RegisterSnapshot): List<RegisterSnapshot>
}