package io.github.tomplum.aoc.sorting.rucksack

class CommonCompartment : PrioritySummationStrategy {
    override fun calculate(rucksacks: List<String>): Int = rucksacks.sumOf { rucksack ->
        val compartments = rucksack.chunked(rucksack.length / 2)
        val duplicate = compartments[0].find { fromFirst -> compartments[1].contains(fromFirst) }
        duplicate!!.getAlphabetIndex()
    }
}