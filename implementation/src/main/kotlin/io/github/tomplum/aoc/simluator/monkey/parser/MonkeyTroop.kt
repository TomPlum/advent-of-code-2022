package io.github.tomplum.aoc.simluator.monkey.parser

import io.github.tomplum.libs.extensions.product

/**
 * A collection of [Monkey]s.
 * @param monkeys The monkey members of the troop
 */
class MonkeyTroop(val monkeys: List<Monkey>) {
    /**
     * The level of monkey business is calculated by
     * multiplying the total number of item inspections
     * made by the two most active members of the troop.
     * @return The total level of monkey business
     */
    fun calculateMonkeyBusiness(): Long = monkeys
        .map { monkey -> monkey.inspections }
        .sortedDescending()
        .take(2)
        .product()
}