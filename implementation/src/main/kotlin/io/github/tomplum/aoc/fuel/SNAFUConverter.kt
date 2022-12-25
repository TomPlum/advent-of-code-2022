package io.github.tomplum.aoc.fuel

import kotlin.math.pow

class SNAFUConverter(private val input: List<String>) {

    // Regular Binary
    // 16 8 4 2 1 0
    //    1 0 1 0 0 = 10

    // SNAFU Binary
    // 125 25 5 1
    //        2 0 = 10

    // Decimal: 4 3 2  1  0
    // Snafu:   2 1 0 -1 -2

    fun getConsoleInput(): String {
        val decimal = input.sumOf { snafu -> convertToDecimal(snafu) }
        return convertToSnafu(decimal)
    }

    private fun convertToDecimal(snafu: String): Double = snafu
        .reversed()
        .mapIndexed { i, char ->
            5.0.pow(i) * when (char) {
                '-' -> -1
                '=' -> -2
                else -> char.toString().toInt()
            }
        }.sum()

    private fun convertToSnafu(decimal: Double): String {
        var snafu = ""
        var remaining = decimal.toInt()
        while(remaining > 0) {
            when(remaining % 5) {
                0 -> {
                    snafu = "0$snafu"
                }
                1 -> {
                    snafu = "1$snafu"
                    remaining += -1
                }
                2 -> {
                    snafu = "2$snafu"
                    remaining += -2
                }
                3 -> {
                    snafu = "=$snafu"
                    remaining += 2
                }
                4 -> {
                    snafu = "-$snafu"
                    remaining += 1
                }
                else -> throw IllegalArgumentException("Invalid SNAFU remainder")
            }
            remaining /= 5
        }

        return snafu
    }
}