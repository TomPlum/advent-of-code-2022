package io.github.tomplum.aoc.simluator.keepsafe.monkey

import java.util.Stack
import io.github.tomplum.aoc.simluator.keepsafe.KeepAway
import io.github.tomplum.aoc.simluator.keepsafe.strategy.WorryReliefStrategy

/**
 * A singular monkey playing a game of [KeepAway]
 * @param id A unique identifier for the monkey amongst the [MonkeyTroop]
 * @param items The worry level of the items currently in the monkey's possession
 * @param operation The operation used by the monkey to influence the worry level
 * @param test Used by the monkey to test the worry level and determine its next target monkey
 */
data class Monkey(
    val id: Int,
    val items: Stack<Long>,
    val operation: MonkeyOperation,
    val test: MonkeyTest
) {
    /**
     * The total number of times the monkey
     * has inspected an item.
     */
    var inspections = 0L

    /**
     * Inspects the next of the [items] using
     * the monkey's [operation].
     *
     * @param strategy The strategy for worry relief
     * @return The relieved worry value
     */
    fun inspect(strategy: WorryReliefStrategy): Long {
        inspections++
        val worry = operation.execute(items.pop())
        return strategy.reduce(worry)
    }
}