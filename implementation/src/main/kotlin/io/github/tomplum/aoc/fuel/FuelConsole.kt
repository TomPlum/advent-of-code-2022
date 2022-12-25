package io.github.tomplum.aoc.fuel

class FuelConsole(private val values: List<String>) {
    fun getConsoleInput(): String {
        return SNAFU.fromDecimal(values.sumOf { value -> SNAFU(value).toDecimal() })
    }
}