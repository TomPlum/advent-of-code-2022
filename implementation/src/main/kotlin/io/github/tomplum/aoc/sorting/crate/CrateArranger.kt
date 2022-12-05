package io.github.tomplum.aoc.sorting.crate

import io.github.tomplum.aoc.sorting.crate.strategy.CrateSortingStrategy

class CrateArranger(private val drawing: List<String>) {
    fun consolidate(strategy: CrateSortingStrategy): String {
        val ( stacks, instructions ) = CrateArrangementPlan.fromDrawing(drawing)
        val sorted = strategy.sort(instructions, stacks)
        return sorted.map { stack -> stack.peek() }.joinToString("")
    }
}