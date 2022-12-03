package io.github.tomplum.aoc.sorting.strategy

class GroupBadgePriority : PrioritySummationStrategy {
    override fun calculate(rucksacks: List<String>): Int = rucksacks.chunked(3).map { group ->
        val distinctItems = group.joinToString().toList().distinct()
        val badge = distinctItems.find { item -> group.all { rucksack -> rucksack.contains(item) } }
        badge!!
    }.sumOf { badge -> badge.getAlphabetIndex() }
}