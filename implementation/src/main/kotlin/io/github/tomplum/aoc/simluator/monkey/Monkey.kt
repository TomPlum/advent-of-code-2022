package io.github.tomplum.aoc.simluator.monkey

data class Monkey(
    val id: Int,
    val items: MutableList<Long>,
    val operation: MonkeyOperation,
    val test: MonkeyTest
)