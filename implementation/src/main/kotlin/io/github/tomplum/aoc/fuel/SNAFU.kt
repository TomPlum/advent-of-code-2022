package io.github.tomplum.aoc.fuel

import kotlin.math.pow

class SNAFU(private val value: String) {

    companion object {
        private val mappings = listOf(
            Mapping('0', 0),
            Mapping('1', -1),
            Mapping('2', -2),
            Mapping('=', 2),
            Mapping('-', 1)
        )

        fun fromDecimal(decimal: Long): String {
            var snafu = ""
            var remaining = decimal

            while (remaining != 0L) {
                val remainder = (remaining % 5).toInt()
                val mapping = mappings[remainder]
                snafu = "${mapping.prefix}$snafu"
                remaining += mapping.offset
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

    data class Mapping(val prefix: Char, val offset: Int)
}