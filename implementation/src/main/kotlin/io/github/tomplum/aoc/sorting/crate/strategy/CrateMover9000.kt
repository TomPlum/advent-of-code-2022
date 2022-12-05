package io.github.tomplum.aoc.sorting.crate.strategy

import io.github.tomplum.aoc.sorting.crate.Instruction
import java.util.*

class CrateMover9000 : CrateSortingStrategy {
    override fun sort(instructions: List<Instruction>, stacks: List<Stack<Char>>): List<Stack<Char>> =
        instructions.fold(stacks) { acc, instruction ->
            repeat(instruction.quantity) {
                val crate = acc[instruction.from].pop()
                acc[instruction.to].push(crate)
            }
            acc
        }
}