package io.github.tomplum.aoc.map.volcano

data class Valve(val label: String) : Comparable<Valve> {
    val index = label[0].toValue() + label[1].toValue()

    private fun Char.toValue() = this.lowercase().first().code - 'a'.code

    override fun compareTo(other: Valve): Int {
        return this.index - other.index
    }
}