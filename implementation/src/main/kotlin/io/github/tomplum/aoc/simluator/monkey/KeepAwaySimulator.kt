package io.github.tomplum.aoc.simluator.monkey

import io.github.tomplum.aoc.simluator.monkey.strategy.WorryLevelStrategy
import io.github.tomplum.libs.extensions.product

class KeepAwaySimulator(private val monkeys: List<Monkey>) {

    fun simulate(rounds: Int, strategy: WorryLevelStrategy): Long {
        repeat(rounds) {
            monkeys.forEach { monkey ->
                monkey.items.forEach { item ->
                    val worryLevel = strategy.calculate(monkey.operation.execute(item))
                    monkey.inspections++
                    val targetMonkey = monkey.test.execute(worryLevel)
                    monkeys[targetMonkey].items.add(worryLevel)
                }
                monkey.items.clear()
            }
        }

        return monkeys.map { monkey -> monkey.inspections }.sortedDescending().take(2).product()
    }
}