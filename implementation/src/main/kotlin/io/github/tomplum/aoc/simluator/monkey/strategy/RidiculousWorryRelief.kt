package io.github.tomplum.aoc.simluator.monkey.strategy

import io.github.tomplum.aoc.simluator.monkey.parser.Monkey
import io.github.tomplum.aoc.simluator.monkey.parser.MonkeyTroop
import io.github.tomplum.aoc.simluator.monkey.parser.MonkeyTest
import io.github.tomplum.aoc.simluator.monkey.KeepAway

/**
 * You're worried you might not ever get your items back.
 * So worried, in fact, that your relief that a monkey's
 * inspection didn't damage an item no longer causes your
 * worry level to be divided by three.
 *
 * @param monkeys The members of the [MonkeyTroop] participating in [KeepAway]
 */
class RidiculousWorryRelief(monkeys: List<Monkey>) : WorryReliefStrategy {

    /**
     * The lowest common multiple of all the [MonkeyTest] divisors.
     */
    private val divisorLcm = monkeys.map { monkey -> monkey.test.divisor }.reduce { a, b -> a * b }.toLong()

    /**
     * Heavily relieves the worry value by using
     * some modular arithmetic. Uses the [divisorLcm]
     * to reduce the [previous] value by returning the
     * remainder after apply the modulus function.
     *
     * @param previous The worry level after the inspection
     * @return The reduced worry level after the relief
     */
    override fun reduce(previous: Long): Long {
        return previous % divisorLcm
    }
}