package io.github.tomplum.aoc.simluator.monkey.strategy

import io.github.tomplum.aoc.simluator.monkey.Monkey

class RidiculousWorryRelief(monkeys: List<Monkey>) : WorryLevelStrategy {

    private val mod = monkeys.map { monkey -> monkey.test.divisor }.reduce { a, b -> a * b }.toLong()

    override fun calculate(previous: Long): Long {
        return previous % mod
    }
}