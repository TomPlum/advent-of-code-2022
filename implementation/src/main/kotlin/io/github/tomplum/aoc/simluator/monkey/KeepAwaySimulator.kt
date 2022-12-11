package io.github.tomplum.aoc.simluator.monkey

import io.github.tomplum.libs.extensions.product

class KeepAwaySimulator(notes: List<String>) {

    private val monkeys = MonkeyNoteParser().parse(notes)

    private val inspections = mutableMapOf(*monkeys.map { Pair(it.id, 0) }.toTypedArray())

    fun simulate(rounds: Int): Int {
        (1..rounds).forEach { round ->
            monkeys.forEach { monkey ->
                monkey.items.forEach { item ->
                    val worryLevel = monkey.operation.execute(item) / 3
                    inspections.computeIfPresent(monkey.id) { _, count  -> count +  1 }
                    val targetMonkey = monkey.test.execute(worryLevel)
                    monkeys[targetMonkey].items.add(worryLevel)
                }
                monkey.items.clear()
            }
        }
        return inspections.values.sortedDescending().take(2).product()
    }
}