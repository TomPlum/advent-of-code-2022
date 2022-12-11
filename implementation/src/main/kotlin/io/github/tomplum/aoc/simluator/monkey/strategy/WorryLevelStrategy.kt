package io.github.tomplum.aoc.simluator.monkey.strategy

interface WorryLevelStrategy {
    fun calculate(previous: Long): Long
}