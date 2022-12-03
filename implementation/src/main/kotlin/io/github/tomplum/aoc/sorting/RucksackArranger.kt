package io.github.tomplum.aoc.sorting

import io.github.tomplum.libs.logging.AdventLogger

class RucksackArranger(private val rucksacks: List<String>) {
    fun getDuplicateItemPrioritySum(): Int = rucksacks.sumOf { rucksack ->
        AdventLogger.debug("Finding cross-compartment duplicate from [$rucksack]")
        val compartments = rucksack.chunked(rucksack.length / 2)
        val duplicate = compartments[0].find { firstChar ->
            compartments[1].contains(firstChar)
        }
        val priority = duplicate!!.getAlphabetIndex()
        AdventLogger.debug("Found duplicate [$duplicate] with priority [$priority]")
        priority
    }

    fun getGroupCommonItemsPrioritySum(): Int = rucksacks.chunked(3).map { group ->
        val distinctItems = group.joinToString().toList().distinct()
        val badge = distinctItems.find { item -> group.all { rucksack -> rucksack.contains(item) } }
        badge!!
    }.sumOf { badge -> badge.getAlphabetIndex() }

    private fun Char.getAlphabetIndex(): Int {
        val startingIndex = 'a'.code
        val index = this.lowercase().first().code - startingIndex + 1
        return if (this.isLowerCase()) index else index + 26
    }
}