package io.github.tomplum.aoc.simluator.keepsafe.strategy

import io.github.tomplum.aoc.simluator.keepsafe.KeepAway
import io.github.tomplum.aoc.simluator.keepsafe.monkey.MonkeyTroop

/**
 * You're worried you might not ever get your items back.
 * So worried, in fact, that your relief that a monkey's
 * inspection didn't damage an item no longer causes your
 * worry level to be divided by three.
 *
 * @param monkeys The members of the [MonkeyTroop] participating in [KeepAway]
 */
class RidiculousWorryRelief(private val monkeys: MonkeyTroop) : WorryReliefStrategy {

    /**
     * The divisor of the [reduce] modulus function used to relieve worry.
     */
    private val divisorLcm = monkeys.findTestDivisorLowestCommonMultiple()

    /**
     * Heavily relieves the worry value by using
     * some modular arithmetic. Uses the divisor LCM
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