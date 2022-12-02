package io.github.tomplum.aoc.game

/**
 * Possible outcomes of a game of [RockPaperScissors].
 * @param score The number of points added at the end of a round
 */
enum class RoundOutcome(val score: Int) {
    LOSS(0),
    DRAW(3),
    WIN(6)
}