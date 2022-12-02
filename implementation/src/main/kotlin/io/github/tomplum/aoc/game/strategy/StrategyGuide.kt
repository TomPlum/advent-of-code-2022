package io.github.tomplum.aoc.game.strategy

import io.github.tomplum.aoc.game.Throw
import io.github.tomplum.aoc.game.RockPaperScissors

/**
 * A strategy guide for playing a game of [RockPaperScissors].
 * Explains how to determine your response relative to the opponents throw
 * and how to score your rounds.
 */
interface StrategyGuide {
    /**
     * Determines the total score of a given round relative to the
     * [opponentsThrow] and [yourResponse].
     *
     * @param opponentsThrow The opponents chosen throw
     * @param yourResponse Your responding throw
     * @return The score of the winner for the round
     */
    fun determineRoundScore(opponentsThrow: Throw, yourResponse: Throw): Int
}