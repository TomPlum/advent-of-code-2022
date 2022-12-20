package io.github.tomplum.extension

fun Collection<String>.splitNewLine(): List<List<String>> = this.fold(
    mutableListOf<MutableList<String>>(mutableListOf())
) { acc, line ->
    if (line.isNotBlank()) {
        acc.last().add(line)
    } else {
        acc.add(mutableListOf())
    }
    acc
}

fun List<Long>.lcm(): Long {
    var result = this[0]
    this.forEachIndexed { i, _ -> result = lcm(result, this[i]) }
    return result
}

private fun lcm(a: Long, b: Long) = a * (b / gcd(a, b))

private fun gcd(a: Long, b: Long): Long {
    var n1 = a
    var n2 = b
    while (n1 != n2) {
        if (n1 > n2) n1 -= n2 else n2 -= n1
    }
    return n1
}