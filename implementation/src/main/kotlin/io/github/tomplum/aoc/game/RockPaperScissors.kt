package io.github.tomplum.aoc.game

import io.github.tomplum.aoc.game.strategy.StrategyGuide

class RockPaperScissors(private val rounds: List<String>) {
    fun play(strategyGuide: StrategyGuide) = rounds.sumOf { round ->
        val opponentsThrow = round.first().toRockPaperScissorsThrow()
        val yourThrow = round.last().toRockPaperScissorsThrow()
        strategyGuide.determineRoundScore(opponentsThrow, yourThrow)
    }
    
    private fun Char.toRockPaperScissorsThrow() = when(this) {
        'A' -> Throw.ROCK
        'X' -> Throw.ROCK
        'B' -> Throw.PAPER
        'Y' -> Throw.PAPER
        'C' -> Throw.SCISSORS
        'Z' -> Throw.SCISSORS
        else -> throw IllegalAccessException("Unknown Throw Code [$code]")
    }
}