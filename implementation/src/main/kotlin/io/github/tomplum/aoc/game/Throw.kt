package io.github.tomplum.aoc.game

/**
 * Represents a "throw" in a game of [RockPaperScissors]
 * @param score The number of points added when winning a round
 */
enum class Throw(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3)
}