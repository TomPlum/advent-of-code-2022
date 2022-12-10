package io.github.tomplum.aoc.communication.cpu

class Add(private val value: Int) : Instruction {

    companion object {
        fun fromString(string: String): Add {
            val valueString = string.removePrefix("addx ").trim()
            if (valueString.startsWith("-")) {
                return Add(-valueString.removePrefix("-").toInt())
            }
            return Add(valueString.toInt())
        }
    }

    override fun execute(currentRegisterValue: Int): Int {
        return currentRegisterValue + value
    }
}