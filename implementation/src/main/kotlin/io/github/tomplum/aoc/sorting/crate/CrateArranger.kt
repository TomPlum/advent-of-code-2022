package io.github.tomplum.aoc.sorting.crate

import io.github.tomplum.aoc.sorting.crate.strategy.CrateSortingStrategy

/**
 * The expedition can depart as soon as the final supplies have been unloaded from the ships.
 * Supplies are stored in stacks of marked crates, but because the needed supplies are buried
 * under many other crates, the crates need to be rearranged.
 *
 * The ship has a giant cargo crane capable of moving crates between stacks.
 * To ensure none of the crates get crushed or fall over, the crane operator will rearrange
 * them in a series of carefully-planned steps. After the crates are rearranged, the desired
 * crates will be at the top of each stack.
 *
 * @param drawing A drawing of the [CrateArrangementPlan]
 */
class CrateArranger(private val drawing: List<String>) {
    /**
     * Consolidates all the crates according to the
     * given [drawing] as interpreted by the sorting [strategy].
     *
     * @param strategy The sorting strategy for the model of cargo crane
     * @return A string of crate IDs at the top of the stacks after sorting
     */
    fun consolidate(strategy: CrateSortingStrategy): String {
        val ( stacks, instructions ) = CrateArrangementPlan.fromDrawing(drawing)
        val sorted = strategy.sort(instructions, stacks)
        return sorted.map { stack -> stack.peek() }.joinToString("")
    }
}