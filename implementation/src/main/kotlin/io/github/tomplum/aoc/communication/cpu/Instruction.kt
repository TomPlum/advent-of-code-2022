package io.github.tomplum.aoc.communication.cpu

interface Instruction {
    fun execute(value: Int): Int
}