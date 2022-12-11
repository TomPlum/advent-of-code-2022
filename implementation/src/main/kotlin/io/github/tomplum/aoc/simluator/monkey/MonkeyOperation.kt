package io.github.tomplum.aoc.simluator.monkey

class MonkeyOperation(private val instruction: String) {
    fun execute(worryLevel: Int): Int {
        val contents = instruction.trim().removePrefix("Operation: ").trim()
        val end = contents.removePrefix("new = old ")
        val operator = end.first()
        val valueString = end.drop(1).trim()
        val value = if (valueString == "old") worryLevel else valueString.toInt()
        return when(operator) {
            '+' -> worryLevel + value
            '*' -> worryLevel * value
            else -> throw IllegalArgumentException("Unknown monkey operation operator value [$operator]")
        }
    }
}