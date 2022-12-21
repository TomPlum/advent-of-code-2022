package io.github.tomplum.aoc.game.riddle

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class MonkeyRiddleTest {
    private val jobs = TestInputReader.read<String>("day21/example.txt").value
    private val riddle = MonkeyRiddle(jobs)

    @Test
    fun examplePartOne() {
        assertThat(riddle.numberYelledByRootMonkey()).isEqualTo(152)
    }

    @Test
    fun examplePartTwo() {
        assertThat(riddle.numberYelledByUs()).isEqualTo(301)
    }
}