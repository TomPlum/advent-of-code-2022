package io.github.tomplum.aoc.simluator.keepsafe

import io.github.tomplum.aoc.simluator.keepsafe.monkey.MonkeyTroop
import io.github.tomplum.aoc.simluator.keepsafe.strategy.WorryReliefStrategy

/**
 * As you finally start making your way upriver, you realize your pack is
 * much lighter than you remember. Just then, one of the items from your
 * pack goes flying overhead. Monkeys are playing Keep Away with your
 * missing things!
 *
 * To get your stuff back, you need to be able to predict where the monkeys
 * will throw your items. After some careful observation, you realize the
 * monkeys operate based on how worried you are about each item.
 */
class KeepAway {
    /**
     * Plays a single round of the game with the given
     * [troop] and calculates the worry level using the
     * given [strategy].
     *
     * @param troop The monkeys participating in the game
     * @param strategy The strategy for worry relief
     * @return The state of the monkey players at the end of the round
     */
    fun play(troop: MonkeyTroop, strategy: WorryReliefStrategy) = troop.monkeys.forEach { monkey ->
        val items = monkey.items
        while(items.isNotEmpty()) {
            val worryLevel = strategy.reduce(monkey.operation.execute(items.pop()))
            monkey.inspections++
            val targetMonkey = monkey.test.execute(worryLevel)
            troop.throwItem(targetMonkey, worryLevel)
        }
    }
}