package io.github.tomplum.aoc.simluator.monkey.strategy

/**
 * After each monkey inspects an item but before it tests
 * your worry level, your relief that the monkey's inspection
 * didn't damage the item causes your worry level to be divided
 * by three and rounded down to the nearest integer.
 */
class WorryRelief : WorryReliefStrategy {
    /**
     * Simply reduces the worry level to a third.
     *
     * @param previous The worry level after the inspection
     * @return The reduced worry level after the relief
     */
    override fun reduce(previous: Long): Long {
        return previous / 3
    }
}