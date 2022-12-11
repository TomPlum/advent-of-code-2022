package io.github.tomplum.aoc.simluator.monkey

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import io.github.tomplum.aoc.simluator.monkey.strategy.RidiculousWorryRelief
import io.github.tomplum.aoc.simluator.monkey.strategy.WorryRelief
import org.junit.jupiter.api.Test

class KeepAwaySimulatorTest {
    private val notes = TestInputReader.read<String>("day11/example.txt").value
    private val monkeys = MonkeyNoteParser().parse(notes)
    private val simulator = KeepAwaySimulator(monkeys)

    @Test
    fun examplePartOne() {
        assertThat(simulator.simulate(20, WorryRelief())).isEqualTo(10605)
    }

    @Test
    fun examplePartTwo() {
        val strategy = RidiculousWorryRelief(monkeys)
        assertThat(simulator.simulate(10000, strategy)).isEqualTo(2713310158)
    }
}