package io.github.tomplum.aoc.game

class RockPaperScissors(private val strategyGuide: List<String>) {
    fun play() = strategyGuide.map { round ->
        val opponentsThrow = getThrow(round.first())
        val yourThrow = getThrow(round.last())
        val outcome = determineOutcome(opponentsThrow, yourThrow)
        outcome.score + yourThrow.score
    }.sum()

    
    private fun getThrow(code: Char) = when(code) {
        'A' -> Throw.ROCK
        'X' -> Throw.ROCK
        'B' -> Throw.PAPER
        'Y' -> Throw.PAPER
        'C' -> Throw.SCISSORS
        'Z' -> Throw.SCISSORS
        else -> throw IllegalAccessException("Unknown Throw Code [$code]")
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