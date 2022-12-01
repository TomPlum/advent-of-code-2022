package io.github.tomplum.aoc

class CalorieCounter(private val list: List<String>) {
    /**
     * Calculates the highest sum of calories from the
     * given [list] of Elves calorific data.
     * @param n The number of values to sum from the top of the list.
     */
    fun getHighestNSum(n: Int): Int = list
        .fold(mutableListOf(0)) { sums, value ->
            when {
                value.isNotEmpty() -> sums[sums.lastIndex] = value.toInt() + sums.last()
                else -> sums.add(0)
            }
            sums
        }
        .sortedDescending()
        .take(n)
        .sum()
}