package io.github.tomplum.aoc.game.strategy

import io.github.tomplum.aoc.game.RoundOutcome
import io.github.tomplum.aoc.game.Throw

class CorrectStrategyGuide : StrategyGuide {
    override fun determineRoundScore(opponentsThrow: Throw, yourResponse: Throw): Int {
        val neededOutcome = determineNeededOutcome(yourResponse)
        val yourThrow = calculateThrowForOutcome(neededOutcome, opponentsThrow)
        return neededOutcome.score + yourThrow.score
    }

    private fun determineNeededOutcome(yourResponse: Throw): RoundOutcome = when(yourResponse) {
        Throw.ROCK -> RoundOutcome.LOSS
        Throw.PAPER -> RoundOutcome.DRAW
        Throw.SCISSORS -> RoundOutcome.WIN
    }

    private fun calculateThrowForOutcome(outcome: RoundOutcome, opponentsThrow: Throw): Throw {
        return when(outcome) {
            RoundOutcome.WIN -> when(opponentsThrow) {
                Throw.ROCK -> Throw.PAPER
                Throw.PAPER -> Throw.SCISSORS
                Throw.SCISSORS -> Throw.ROCK
            }
            RoundOutcome.DRAW -> opponentsThrow
            RoundOutcome.LOSS -> when(opponentsThrow) {
                Throw.ROCK -> Throw.SCISSORS
                Throw.PAPER -> Throw.ROCK
                Throw.SCISSORS -> Throw.PAPER
            }
        }
    }
}