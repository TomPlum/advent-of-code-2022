package io.github.tomplum.aoc.sorting.crate

/**
 * A singular instruction for moving n crates from one stack to another.
 * @param quantity The number of crates to move
 * @param from The stack number to move from as per the [CrateArrangementPlan]
 * @param to The stack number to move to as per the [CrateArrangementPlan]
 */
class Instruction private constructor(val quantity: Int, val from: Int, val to: Int) {
    companion object {
        /**
         * Parses an [Instruction] from an entry in the rearrangement
         * procedures of a [CrateArrangementPlan] drawing.
         *
         * Stack identifiers [from] and [to] are parsed as zero-based indexes.
         *
         * @param value The instruction value
         * @return The parsed instruction
         */
        fun fromString(value: String): Instruction {
            val quantity = value.substringAfter("move ").substringBefore(" from").toInt()
            val sourceStackIndex = value.substringAfter("from ").substringBefore(" to").toInt() - 1
            val targetStackIndex = value.substringAfter("to ").trim().toInt() - 1
            return Instruction(quantity, sourceStackIndex, targetStackIndex)
        }
    }
}