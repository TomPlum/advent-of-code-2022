package io.github.tomplum.aoc.simluator.monkey.parser

import io.github.tomplum.libs.extensions.product

class MonkeyTroop(val monkeys: List<Monkey>) {
    fun calculateMonkeyBusiness(): Long = monkeys
        .map { monkey -> monkey.inspections }
        .sortedDescending()
        .take(2)
        .product()
}