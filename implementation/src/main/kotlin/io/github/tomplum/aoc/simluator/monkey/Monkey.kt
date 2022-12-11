package io.github.tomplum.aoc.simluator.monkey

data class Monkey(
    val id: Int,
    val items: MutableList<Int>,
    val operation: MonkeyOperation,
    val test: MonkeyTest
)