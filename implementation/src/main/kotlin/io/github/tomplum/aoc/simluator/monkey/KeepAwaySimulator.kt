package io.github.tomplum.aoc.simluator.monkey

import io.github.tomplum.aoc.simluator.monkey.strategy.WorryLevelStrategy

class KeepAwaySimulator(private val troop: MonkeyTroop) {
    private val monkeys = troop.monkeys

    fun simulate(rounds: Int, strategy: WorryLevelStrategy) = repeat(rounds) {
        monkeys.forEach { monkey ->
            val items = monkey.items
            while(items.isNotEmpty()) {
                val worryLevel = strategy.calculate(monkey.operation.execute(items.pop()))
                monkey.inspections++
                val targetMonkey = monkey.test.execute(worryLevel)
                monkeys[targetMonkey].items.add(worryLevel)
            }
        }
    }.let { troop.calculateMonkeyBusiness() }
}