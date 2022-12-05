package io.github.tomplum.aoc.sorting.crate.strategy

import io.github.tomplum.aoc.sorting.crate.Instruction
import java.util.*

class CrateMover9000 : CrateSortingStrategy {
    override fun sort(instructions: List<Instruction>, stacks: List<Stack<Char>>): List<Stack<Char>> {
        instructions.forEach { instruction ->
            repeat(instruction.quantity) {
                val crate = stacks[instruction.from].pop()
                stacks[instruction.to].push(crate)
            }
        }

        return stacks
    }
}