package io.github.tomplum.aoc.game.strategy

import io.github.tomplum.aoc.game.RoundOutcome
import io.github.tomplum.aoc.game.Throw

/**
 * A naive strategy guide implementation that makes assumptions
 * about how your responding throws work.
 */
class AssumedStrategyGuide : StrategyGuide {
    override fun determineRoundScore(opponentsThrow: Throw, yourResponse: Throw): Int {
        val outcome = determineOutcome(opponentsThrow, yourResponse)
        return outcome.score + yourResponse.score
    }

    private fun determineOutcome(opponentsThrow: Throw, yourResponse: Throw): RoundOutcome {
        if (yourResponse == opponentsThrow) {
            return RoundOutcome.DRAW
        }

        if (
            yourResponse == Throw.ROCK && opponentsThrow == Throw.SCISSORS ||
            yourResponse == Throw.PAPER && opponentsThrow == Throw.ROCK ||
            yourResponse == Throw.SCISSORS && opponentsThrow == Throw.PAPER
        ) {
            return RoundOutcome.WIN
        }

        return RoundOutcome.LOSS
    }
}