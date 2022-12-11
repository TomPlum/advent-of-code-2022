package io.github.tomplum.aoc.simluator.monkey

import java.math.BigInteger

class MonkeyOperation(instruction: String) {
    private val contents = instruction.trim().removePrefix("Operation: ").trim()
    private val end = contents.removePrefix("new = old ")
    private val operator = end.first()
    private val valueString = end.drop(1).trim()

    fun execute(worryLevel: BigInteger): BigInteger {
        val value = if (valueString == "old") worryLevel else valueString.toBigInteger()
        return when(operator) {
            '+' -> worryLevel + value
            '*' -> worryLevel * value
            else -> throw IllegalArgumentException("Unknown monkey operation operator value [$operator]")
        }
    }
}