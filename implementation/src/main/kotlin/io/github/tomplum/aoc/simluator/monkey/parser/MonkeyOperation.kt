package io.github.tomplum.aoc.simluator.monkey.parser

class MonkeyOperation(instruction: String) {
    private val contents = instruction.trim().removePrefix("Operation: ").trim()
    private val end = contents.removePrefix("new = old ")
    private val operator = end.first()
    private val valueString = end.drop(1).trim()
    private val isValueOld = valueString == "old"

    fun execute(worryLevel: Long): Long {
        val value = if (isValueOld) worryLevel else valueString.toLong()
        return when(operator) {
            '+' -> worryLevel + value
            '*' -> worryLevel * value
            else -> throw IllegalArgumentException("Unknown monkey operation operator value [$operator]")
        }
    }
}