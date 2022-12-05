package io.github.tomplum.aoc.sorting.crate.strategy

import io.github.tomplum.aoc.sorting.crate.Instruction
import java.util.Stack

interface CrateSortingStrategy {
    fun sort(instructions: List<Instruction>, stacks: List<Stack<Char>>): List<Stack<Char>>
}