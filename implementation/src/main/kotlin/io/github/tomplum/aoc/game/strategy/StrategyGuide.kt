package io.github.tomplum.aoc.game.strategy

import io.github.tomplum.aoc.game.Throw

interface StrategyGuide {
    fun determineRoundScore(opponentsThrow: Throw, yourResponse: Throw): Int
}