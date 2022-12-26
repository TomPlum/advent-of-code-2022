package io.github.tomplum.aoc.fuel

import kotlin.math.pow

private val mappings = listOf(
    SNAFU.Mapping('0', 0),
    SNAFU.Mapping('1', -1),
    SNAFU.Mapping('2', -2),
    SNAFU.Mapping('=', 2),
    SNAFU.Mapping('-', 1)
)

fun Long.toSNAFU(): String {
    var snafu = ""
    var decimal = this

    while (decimal != 0L) {
        val remainder = (decimal % 5).toInt()
        val mapping = mappings[remainder]
        snafu = "${mapping.prefix}$snafu"
        decimal += mapping.offset
        decimal /= 5
    }

    return snafu
}

class SNAFU(private val value: String) {

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