package io.github.tomplum.aoc.simluator.monkey.strategy

class WorryRelief : WorryLevelStrategy {
    override fun calculate(previous: Long): Long {
        return previous / 3
    }
}