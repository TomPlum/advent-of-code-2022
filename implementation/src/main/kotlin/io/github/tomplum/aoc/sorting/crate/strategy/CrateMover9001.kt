package io.github.tomplum.aoc.sorting.crate.strategy

import io.github.tomplum.aoc.sorting.crate.Instruction
import java.util.*

class CrateMover9001 : CrateSortingStrategy {
    override fun sort(instructions: List<Instruction>, stacks: List<Stack<Char>>): List<Stack<Char>> {
        instructions.forEach { instruction ->
            val crates = (1..instruction.quantity).map { stacks[instruction.from].pop() }.reversed()
            crates.forEach { crate -> stacks[instruction.to].push(crate) }
        }
        return stacks
    }
}