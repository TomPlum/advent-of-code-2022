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
}