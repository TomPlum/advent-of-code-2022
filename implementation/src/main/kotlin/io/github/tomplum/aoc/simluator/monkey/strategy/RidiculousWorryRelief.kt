package io.github.tomplum.aoc.simluator.monkey.strategy

import io.github.tomplum.aoc.simluator.monkey.parser.Monkey

class RidiculousWorryRelief(monkeys: List<Monkey>) : WorryLevelStrategy {

    private val divisorLcm = monkeys.map { monkey -> monkey.test.divisor }.reduce { a, b -> a * b }.toLong()

    override fun calculate(previous: Long): Long {
        return previous % divisorLcm
    }
}