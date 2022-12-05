package io.github.tomplum.aoc.sorting.crate.strategy

import io.github.tomplum.aoc.sorting.crate.Instruction
import java.util.Stack

/**
 * A strategy of sorting crates for a model of giant cargo crane.
 */
interface CrateSortingStrategy {
    /**
     * Sorts the [stacks] of crates using the given [instructions].
     *
     * @param instructions A list of instructions for moving crates
     * @param stacks The initial state of the stacks before sorting
     * @return The stacks after having sorted them
     */
    fun sort(instructions: List<Instruction>, stacks: List<Stack<Char>>): List<Stack<Char>>
}