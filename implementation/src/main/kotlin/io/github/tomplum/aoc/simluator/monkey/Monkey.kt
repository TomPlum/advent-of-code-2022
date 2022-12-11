package io.github.tomplum.aoc.simluator.monkey

data class Monkey(
    val id: Int,
    val startingItems: List<Int>,
    val operation: MonkeyOperation,
    val test: MonkeyTest
)