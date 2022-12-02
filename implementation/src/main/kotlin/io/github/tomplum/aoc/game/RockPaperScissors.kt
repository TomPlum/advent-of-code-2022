package io.github.tomplum.aoc.game

import io.github.tomplum.aoc.game.strategy.StrategyGuide

class RockPaperScissors(private val rounds: List<String>) {
    /**
     * Plays out all the [rounds] of the [RockPaperScissors] game.
     * Score is calculated according to the rules of the given [strategyGuide].
     * @param strategyGuide Governs how the game is played and scored
     * @return The final score of the winning player
     */
    fun play(strategyGuide: StrategyGuide) = rounds.sumOf { round ->
        val opponentsThrow = round.first().toThrow()
        val yourThrow = round.last().toThrow()
        strategyGuide.determineRoundScore(opponentsThrow, yourThrow)
    }

    /**
     * Converts a [Char] instance into a [Throw].
     * @throws IllegalArgumentException if an unknown throw code is used
     * @return A legal throw instance for the game
     */
    private fun Char.toThrow() = when(this) {
        'A' -> Throw.ROCK
        'X' -> Throw.ROCK
        'B' -> Throw.PAPER
        'Y' -> Throw.PAPER
        'C' -> Throw.SCISSORS
        'Z' -> Throw.SCISSORS
        else -> throw IllegalAccessException("Unknown Throw Code [$code]")
    }
}