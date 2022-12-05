package io.github.tomplum.aoc.sorting.crate

import java.util.Stack

/**
 * A plan for sorting stacks of crates with a giant cargo crane.
 * @param stacks The initial starting state of the stacks of crates
 * @param instructions A list of instructions of how to sort the crates
 */
data class CrateArrangementPlan internal constructor(
    val stacks: List<Stack<Char>>,
    val instructions: List<Instruction>
) {
    companion object {
        /**
         * Parses a [CrateArrangementPlan] from a [drawing] of the
         * starting stacks of crates and the rearrangement procedure.
         *
         * @param drawing A list of rows that make up the drawing
         * @return The parsed plan
         */
        fun fromDrawing(drawing: List<String>): CrateArrangementPlan {
            val dividerIndex = drawing.indexOf("")

            val crates = drawing.subList(0, dividerIndex)
            val stackQuantity = crates.last().trim().last().toString().toInt()
            val emptyStacks = (1..stackQuantity).map { Stack<Char>() }
            val stacks = crates.reversed().drop(1).fold(emptyStacks) { stacks, row ->
                val crateIndices = 1..row.length step 4
                crateIndices.map { crateIndex -> row[crateIndex] }.forEachIndexed { stackIndex, crate ->
                    if (crate != ' ') stacks[stackIndex].add(crate)
                }
                stacks
            }

            val instructions = drawing.subList(dividerIndex + 1, drawing.lastIndex + 1)
                .map { value -> Instruction.fromString(value) }

            return CrateArrangementPlan(stacks, instructions)
        }
    }
}