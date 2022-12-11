package io.github.tomplum.aoc.simluator.keepsafe

import io.github.tomplum.aoc.simluator.keepsafe.monkey.MonkeyTroop
import io.github.tomplum.aoc.simluator.keepsafe.strategy.WorryReliefStrategy

/**
 * Simulates a game of Keep Away played by a [troop]
 * of Monkeys.
 *
 * @param troop The troop of monkeys playing the game
 */
class KeepAwaySimulator(private val troop: MonkeyTroop) {

    private val game = KeepAway()
    private val monkeys = troop.monkeys

    /**
     * Simulates the game of [KeepAway] for the given number of [rounds].
     * Uses the given [strategy] to calculate the worry level
     * when a monkey inspects an item.
     *
     * @param rounds The number of rounds to play
     * @param strategy The strategy for calculating worry relief
     * @return The level of monkey business after all the rounds
     */
    fun simulate(rounds: Int, strategy: WorryReliefStrategy): Long = repeat(rounds) {
        game.play(monkeys, strategy)
    }.let { troop.calculateMonkeyBusiness() }
}