package io.github.tomplum.aoc.simluator.monkey

class MonkeyTest(instructions: List<String>) {
    private val trueTarget = instructions[1].trim().removePrefix("If true: throw to monkey ").trim().toInt()
    private val falseTarget = instructions[2].trim().removePrefix("If false: throw to monkey ").trim().toInt()

    val divisor = instructions[0].trim().removePrefix("Test: divisible by ").trim().toLong()

    fun execute(worryLevel: Long): Int = if (worryLevel % divisor == 0L) {
        trueTarget
    } else {
        falseTarget
    }
}