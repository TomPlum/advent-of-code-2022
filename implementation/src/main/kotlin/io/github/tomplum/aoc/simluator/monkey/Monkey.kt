package io.github.tomplum.aoc.simluator.monkey

import java.util.Stack

data class Monkey(
    val id: Int,
    val items: Stack<Long>,
    val operation: MonkeyOperation,
    val test: MonkeyTest
) {
    var inspections = 0L
}