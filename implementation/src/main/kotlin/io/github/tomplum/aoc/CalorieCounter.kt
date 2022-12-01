package io.github.tomplum.aoc

class CalorieCounter(private val list: List<String>) {
    fun getHighestNSum(n: Int): Int = list
        .fold(mutableListOf(0)) { acc, value ->
            when {
                value.isNotEmpty() -> acc[acc.lastIndex] = value.toInt() + acc.last()
                else -> acc.add(0)
            }
            acc
        }
        .sortedDescending()
        .take(n)
        .sum()
}