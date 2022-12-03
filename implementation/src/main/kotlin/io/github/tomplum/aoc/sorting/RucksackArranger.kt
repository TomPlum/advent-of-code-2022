package io.github.tomplum.aoc.sorting

import io.github.tomplum.aoc.sorting.strategy.PrioritySummationStrategy

class RucksackArranger(private val rucksacks: List<String>) {
    fun calculateItemPrioritySum(strategy: PrioritySummationStrategy) = strategy.calculate(rucksacks)
}