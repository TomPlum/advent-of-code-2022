package io.github.tomplum.aoc.sorting.crate

import io.github.tomplum.aoc.sorting.crate.strategy.CrateSortingStrategy
import java.util.Stack

class CrateArranger(private val stackDrawing: List<String>) {
    fun consolidate(strategy: CrateSortingStrategy): String {
        val ( crateStacks, instructions ) = parseDrawing()
        val sortedCrateStacks = strategy.sort(instructions, crateStacks)
        return sortedCrateStacks.map { stack -> stack.peek() }.joinToString("")
    }

    private fun parseDrawing(): Pair<List<Stack<Char>>, List<Instruction>> {
        val dividerIndex = stackDrawing.indexOf("")

        val crates = stackDrawing.subList(0, dividerIndex)
        val stackQuantity = crates.last().trim().last().toString().toInt()
        val emptyStacks = (1..stackQuantity).map { Stack<Char>() }
        val stacks = crates.reversed().drop(1).fold(emptyStacks) { stacks, row ->
            val crateIndices = 1..row.length step 4
            crateIndices.map { crateIndex -> row[crateIndex] }.forEachIndexed { stackIndex, crate ->
                if (crate != ' ') stacks[stackIndex].add(crate)
            }
            stacks
        }

        val instructions = stackDrawing.subList(dividerIndex + 1, stackDrawing.lastIndex + 1)
            .map { value -> Instruction.fromString(value) }

        return Pair(stacks, instructions)
    }
}