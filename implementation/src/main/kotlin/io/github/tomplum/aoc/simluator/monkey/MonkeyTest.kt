package io.github.tomplum.aoc.simluator.monkey

class MonkeyTest(private val instructions: List<String>) {
    fun execute(worryLevel: Int): Int {
        val divisor = instructions[0].trim().removePrefix("Test: divisible by ").trim().toInt()
        val trueTarget = instructions[1].trim().removePrefix("If true: throw to monkey ").trim().toInt()
        val falseTarget = instructions[2].trim().removePrefix("If false: throw to monkey ").trim().toInt()
        return if (worryLevel % divisor == 0) trueTarget else falseTarget
    }
}