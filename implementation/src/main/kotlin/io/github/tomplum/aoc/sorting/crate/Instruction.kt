package io.github.tomplum.aoc.sorting.crate

class Instruction private constructor(val quantity: Int, val from: Int, val to: Int) {
    companion object {
        fun fromString(value: String): Instruction {
            val quantity = value.substringAfter("move ").substringBefore(" from").toInt()
            val sourceStackIndex = value.substringAfter("from ").substringBefore(" to").toInt() - 1
            val targetStackIndex = value.substringAfter("to ").trim().toInt() - 1
            return Instruction(quantity, sourceStackIndex, targetStackIndex)
        }
    }
}