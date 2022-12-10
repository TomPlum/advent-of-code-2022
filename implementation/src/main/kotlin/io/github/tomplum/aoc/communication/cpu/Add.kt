package io.github.tomplum.aoc.communication.cpu

data class Add(override val value: Int) : Instruction {

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
            RegisterSnapshot(this, previous.xRegister, previous.cycle + 1),
            RegisterSnapshot(this, previous.xRegister + value, previous.cycle + 2)
        )
    }
}