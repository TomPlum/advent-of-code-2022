package io.github.tomplum.aoc.simluator.monkey.strategy

import io.github.tomplum.aoc.simluator.monkey.parser.Monkey

/**
 * A strategy for reducing the level
 * of worry induced by a [Monkey] after
 * having inspected an item.
 */
interface WorryReliefStrategy {
    /**
     * Reduces the [previous] worry level.
     * @param previous The worry level after the inspection
     * @return The reduced worry level after the relief
     */
    fun reduce(previous: Long): Long
}