package io.github.tomplum.aoc.simluator.monkey

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class MonkeyTestTest {
    @ParameterizedTest
    @ValueSource(ints = [23, 46, 69, 92])
    fun shouldReturnCorrectMonkeyTargetForTrueCase(worryLevel: Int) {
        val test = MonkeyTest(listOf(
            "  Test: divisible by 23",
            "    If true: throw to monkey 2",
            "    If false: throw to monkey 3"
        ))
        val targetMonkey = test.execute(worryLevel)
        assertThat(targetMonkey).isEqualTo(2)
    }

    @ParameterizedTest
    @ValueSource(ints = [-10, 5, 22, 24, 100])
    fun shouldReturnCorrectMonkeyTargetForFalseCase(worryLevel: Int) {
        val test = MonkeyTest(listOf(
            "  Test: divisible by 23",
            "    If true: throw to monkey 2",
            "    If false: throw to monkey 3"
        ))
        val targetMonkey = test.execute(worryLevel)
        assertThat(targetMonkey).isEqualTo(3)
    }
}