package io.github.tomplum.aoc.simluator.keepsafe.monkey

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

    /**
     * Throws an item with a given worry level
     * at another [Monkey] in the troop.
     *
     * @param target The ID of the target monkey
     * @param item The worry level of the item
     */
    fun throwItem(target: Int, item: Long) {
        monkeys[target].items.push(item)
    }
}