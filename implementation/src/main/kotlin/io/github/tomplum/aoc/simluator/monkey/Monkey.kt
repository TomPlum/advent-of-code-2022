package io.github.tomplum.aoc.simluator.monkey

import java.math.BigInteger

data class Monkey(
    val id: Int,
    val items: MutableList<BigInteger>,
    val operation: MonkeyOperation,
    val test: MonkeyTest
)