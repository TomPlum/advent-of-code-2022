package io.github.tomplum.aoc.fuel

class FuelConsole(private val fuelRequirements: List<String>) {
    fun getConsoleInput(): String = fuelRequirements
        .sumOf { value -> SNAFU(value).toDecimal() }
        .toSNAFU()
}