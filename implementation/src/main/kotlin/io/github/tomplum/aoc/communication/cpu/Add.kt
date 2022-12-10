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

    override fun execute(previous: RegisterSnapshot): List<RegisterSnapshot> {
        return listOf(
            RegisterSnapshot(previous.value, previous.cycle + 1),
            RegisterSnapshot(previous.value + value, previous.cycle + 2)
        )
    }
}