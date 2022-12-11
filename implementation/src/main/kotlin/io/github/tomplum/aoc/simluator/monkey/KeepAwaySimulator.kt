package io.github.tomplum.aoc.simluator.monkey

import io.github.tomplum.aoc.simluator.monkey.strategy.WorryLevelStrategy
import io.github.tomplum.libs.extensions.product

class KeepAwaySimulator(private val monkeys: List<Monkey>) {

    private val inspections = mutableMapOf(*monkeys.map { Pair(it.id, 0L) }.toTypedArray())

    fun simulate(rounds: Int, strategy: WorryLevelStrategy): Long {
        repeat(rounds) {
            monkeys.forEach { monkey ->
                monkey.items.forEach { item ->
                    val worryLevel = strategy.calculate(monkey.operation.execute(item))
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