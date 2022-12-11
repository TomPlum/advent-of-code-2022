package io.github.tomplum.aoc.simluator.monkey

import io.github.tomplum.libs.extensions.product

class MonkeyTroop(val monkeys: List<Monkey>) {
    fun calculateMonkeyBusiness(): Long = monkeys.map { monkey -> monkey.inspections }
        .sortedDescending()
        .take(2)
        .product()
}