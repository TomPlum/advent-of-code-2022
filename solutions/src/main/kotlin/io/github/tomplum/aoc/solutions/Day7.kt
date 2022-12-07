package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.fs.FileSystem
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day7 : Solution<Long, Long> {
    private val terminalOutput = InputReader.read<String>(Day(7)).value
    private val fs = FileSystem(terminalOutput)

    override fun part1(): Long {
        return fs.getTotalFileSizeFromDirsNoGreaterThan(100000)
    }
}