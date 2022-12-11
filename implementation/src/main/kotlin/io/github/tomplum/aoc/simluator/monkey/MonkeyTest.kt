package io.github.tomplum.aoc.simluator.monkey

import java.math.BigInteger

class MonkeyTest(instructions: List<String>) {
    private val trueTarget = instructions[1].trim().removePrefix("If true: throw to monkey ").trim().toInt()
    private val falseTarget = instructions[2].trim().removePrefix("If false: throw to monkey ").trim().toInt()

    val divisor = instructions[0].trim().removePrefix("Test: divisible by ").trim().toBigInteger()

    fun execute(worryLevel: BigInteger): Int = if (worryLevel % divisor == 0.toBigInteger()) {
        trueTarget
    } else {
        falseTarget
    }
}