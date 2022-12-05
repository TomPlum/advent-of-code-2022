package io.github.tomplum.aoc.sorting.crate.strategy

import io.github.tomplum.aoc.sorting.crate.Instruction
import java.util.*

/**
 * This model of cargo crane moves multiple crates at a time.
 */
class CrateMover9001 : CrateSortingStrategy {
    override fun sort(instructions: List<Instruction>, stacks: List<Stack<Char>>) =
        instructions.fold(stacks) { acc, instruction ->
            val crates = (1..instruction.quantity).map { acc[instruction.from].pop() }.reversed()
            crates.forEach { crate -> acc[instruction.to].push(crate) }
            acc
        }
}