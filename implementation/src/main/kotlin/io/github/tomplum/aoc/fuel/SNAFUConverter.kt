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

    private fun convertToDecimal(snafu: String): Long = snafu
        .reversed()
        .mapIndexed { i, char ->
            (5.0.pow(i) * when (char) {
                '-' -> -1
                '=' -> -2
                else -> char.toString().toInt()
            }).toLong()
        }.sum()

    private fun convertToSnafu(decimal: Long): String {
        var snafu = ""
        var remaining = decimal
        while(remaining != 0L) {
            when(remaining % 5) {
                0L -> {
                    snafu = "0$snafu"
                }
                1L -> {
                    snafu = "1$snafu"
                    remaining += -1
                }
                2L -> {
                    snafu = "2$snafu"
                    remaining += -2
                }
                3L -> {
                    snafu = "=$snafu"
                    remaining += 2
                }
                4L -> {
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