package io.github.tomplum.aoc.simluator.monkey

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class MonkeyTestTest {
    @ParameterizedTest
    @ValueSource(longs = [23, 46, 69, 92])
    fun shouldReturnCorrectMonkeyTargetForTrueCase(worryLevel: Long) {
        val test = MonkeyTest(listOf(
            "  Test: divisible by 23",
            "    If true: throw to monkey 2",
            "    If false: throw to monkey 3"
        ))
        val targetMonkey = test.execute(worryLevel.toBigInteger())
        assertThat(targetMonkey).isEqualTo(2)
    }

    @ParameterizedTest
    @ValueSource(longs = [-10, 5, 22, 24, 100])
    fun shouldReturnCorrectMonkeyTargetForFalseCase(worryLevel: Long) {
        val test = MonkeyTest(listOf(
            "  Test: divisible by 23",
            "    If true: throw to monkey 2",
            "    If false: throw to monkey 3"
        ))
        val targetMonkey = test.execute(worryLevel.toBigInteger())
        assertThat(targetMonkey).isEqualTo(3)
    }
}