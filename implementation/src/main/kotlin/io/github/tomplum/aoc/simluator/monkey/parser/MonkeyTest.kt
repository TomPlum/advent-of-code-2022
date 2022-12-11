package io.github.tomplum.aoc.simluator.monkey.parser

/**
 * A test used by a [Monkey] to determine which other
 * [Monkey] it should throw an item to.
 *
 * @param instructions A list of lines from notes about the test
 */
class MonkeyTest(instructions: List<String>) {
    /**
     * The ID of the [Monkey] to throw the item to
     * should the worry level pass the test.
     */
    private val trueTarget = instructions[1].trim().removePrefix("If true: throw to monkey ").trim().toInt()

    /**
     * The ID of the [Monkey] to throw the item to
     * should the worry level fail the test.
     */
    private val falseTarget = instructions[2].trim().removePrefix("If false: throw to monkey ").trim().toInt()

    /**
     * The value in which to divide by during the test.
     */
    val divisor = instructions[0].trim().removePrefix("Test: divisible by ").trim().toLong()

    /**
     * Runs the test.
     * @param worryLevel The worry level for the inspected item
     * @return The id of the [Monkey] to throw to
     */
    fun execute(worryLevel: Long): Int {
        return if (worryLevel % divisor == 0L) {
            trueTarget
        } else {
            falseTarget
        }
    }
}