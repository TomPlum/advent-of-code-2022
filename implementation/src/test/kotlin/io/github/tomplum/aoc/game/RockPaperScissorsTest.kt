package io.github.tomplum.aoc.game

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class RockPaperScissorsTest {
    @Test
    fun exampleOne() {
        val input = TestInputReader.read<String>("day2/example.txt").value
        val game = RockPaperScissors(input)
        assertThat(game.play()).isEqualTo(15)
    }

    @Test
    fun exampleTwo() {
        val input = TestInputReader.read<String>("day2/example.txt").value
        val game = RockPaperScissors(input)
        assertThat(game.playNewRules()).isEqualTo(12)
    }
}