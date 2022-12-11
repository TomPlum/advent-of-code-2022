package io.github.tomplum.aoc.simluator.monkey

import io.github.tomplum.libs.extensions.product
import io.github.tomplum.libs.logging.AdventLogger

class KeepAwaySimulator(notes: List<String>) {

    private val monkeys = MonkeyNoteParser().parse(notes)

    private val inspections = mutableMapOf(*monkeys.map { Pair(it.id, 0L) }.toTypedArray())

    fun simulate(rounds: Int): Long {
        val mod = monkeys.map { monkey -> monkey.test.divisor }.reduce { a, b -> a * b }
        (1..rounds).forEach { round ->
            monkeys.forEach { monkey ->
                monkey.items.forEach { item ->
                    val worryLevel = monkey.operation.execute(item) % mod
                    inspections.computeIfPresent(monkey.id) { _, count  -> count +  1 }
                    val targetMonkey = monkey.test.execute(worryLevel)
                    monkeys[targetMonkey].items.add(worryLevel)
                }
                monkey.items.clear()
            }
            if (round == 1 || round == 20 || round % 1000 == 0) {
                AdventLogger.info("== After round $round")
                inspections.forEach { (monkey, count) ->
                    AdventLogger.info("Monkey $monkey inspected items $count times.")
                }
            }
        }

        // TODO: Track inspection count in Monkey obj?
        return inspections.values.sortedDescending().take(2).product()
    }
}