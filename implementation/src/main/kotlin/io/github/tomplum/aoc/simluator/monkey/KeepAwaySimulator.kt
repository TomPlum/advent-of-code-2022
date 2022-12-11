package io.github.tomplum.aoc.simluator.monkey

import io.github.tomplum.libs.extensions.product

class KeepAwaySimulator(notes: List<String>) {

    private val monkeys = MonkeyNoteParser().parse(notes)
    private val mod = monkeys.map { monkey -> monkey.test.divisor }.reduce { a, b -> a * b }
    private val inspections = mutableMapOf(*monkeys.map { Pair(it.id, 0L) }.toTypedArray())

    fun simulate(rounds: Int): Long {
        repeat(rounds) {
            monkeys.forEach { monkey ->
                monkey.items.forEach { item ->
                    val worryLevel = monkey.operation.execute(item) % mod
                    inspections.computeIfPresent(monkey.id) { _, count  -> count +  1 }
                    val targetMonkey = monkey.test.execute(worryLevel)
                    monkeys[targetMonkey].items.add(worryLevel)
                }
                monkey.items.clear()
            }
        }

        // TODO: Track inspection count in Monkey obj?
        return inspections.values.sortedDescending().take(2).product()
    }
}