package io.github.tomplum.aoc.sorting.rucksack

class RucksackArranger(private val rucksacks: List<String>) {
    fun calculateItemPrioritySum(strategy: PrioritySummationStrategy) = strategy.calculate(rucksacks)
}