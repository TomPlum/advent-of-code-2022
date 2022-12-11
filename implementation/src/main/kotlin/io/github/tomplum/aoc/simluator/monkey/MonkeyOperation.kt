package io.github.tomplum.aoc.simluator.monkey

class MonkeyOperation(instruction: String) {
    private val contents = instruction.trim().removePrefix("Operation: ").trim()
    private val end = contents.removePrefix("new = old ")
    private val operator = end.first()
    private val valueString = end.drop(1).trim()

    fun execute(worryLevel: Long): Long {
        val value = if (valueString == "old") worryLevel else valueString.toLong()
        return when(operator) {
            '+' -> worryLevel + value
            '*' -> worryLevel * value
            else -> throw IllegalArgumentException("Unknown monkey operation operator value [$operator]")
        }
    }
}