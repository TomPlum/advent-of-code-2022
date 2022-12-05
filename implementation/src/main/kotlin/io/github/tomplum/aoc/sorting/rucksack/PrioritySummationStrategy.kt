package io.github.tomplum.aoc.sorting.rucksack

interface PrioritySummationStrategy {
    fun calculate(rucksacks: List<String>): Int

    fun Char.getAlphabetIndex(): Int {
        val startingIndex = 'a'.code
        val index = this.lowercase().first().code - startingIndex + 1
        return if (this.isLowerCase()) index else index + 26
    }
}