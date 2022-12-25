package io.github.tomplum.aoc.fuel

import kotlin.math.pow

class SNAFU(private val value: String) {
    companion object {
        fun fromDecimal(decimal: Long): String {
            var snafu = ""
            var remaining = decimal
            while (remaining != 0L) {
                when (remaining % 5) {
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

    fun toDecimal(): Long = value.reversed()
        .mapIndexed { i, char ->
            (5.0.pow(i) * when (char) {
                '-' -> -1
                '=' -> -2
                else -> char.toString().toInt()
            }).toLong()
        }.sum()
}