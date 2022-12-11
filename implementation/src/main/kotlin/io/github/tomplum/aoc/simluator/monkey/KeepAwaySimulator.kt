package io.github.tomplum.aoc.simluator.monkey

import io.github.tomplum.aoc.simluator.monkey.strategy.WorryLevelStrategy

class KeepAwaySimulator(private val troop: MonkeyTroop) {
    private val monkeys = troop.monkeys

    fun simulate(rounds: Int, strategy: WorryLevelStrategy) = repeat(rounds) {
        monkeys.forEach { monkey ->
            monkey.items.forEach { item ->
                val worryLevel = strategy.calculate(monkey.operation.execute(item))
                monkey.inspections++
                val targetMonkey = monkey.test.execute(worryLevel)
                monkeys[targetMonkey].items.add(worryLevel)
            }
            monkey.items.clear()
        }
    }.let { troop.calculateMonkeyBusiness() }
}