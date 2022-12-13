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