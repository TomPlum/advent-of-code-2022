package io.github.tomplum.aoc

class CalorieCounter(private val list: List<String>) {
    fun getHighestCalorieCount(): Int {
        var highest = 0
        var current = 0
        list.forEach {
            if (it == "") {
                if (current > highest) {
                    highest = current
                }
                current = 0
            } else {
                current += it.toInt()
            }
        }
        return highest
    }

    fun getTopThreeCalorieCounts(): List<Int> {
        val counts = mutableListOf<Int>()
        var current = 0
        list.forEach { count ->
            if (count == "") {
                counts.add(current)
                current = 0
            } else {
                current += count.toInt()
            }
        }
        counts.add(current)
        return counts.sortedDescending().take(3)
    }
}