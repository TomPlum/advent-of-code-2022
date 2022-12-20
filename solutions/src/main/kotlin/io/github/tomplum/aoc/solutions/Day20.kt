package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.communication.encryption.EncryptedFile
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day20 : Solution<Long, Long> {
    private val contents = InputReader.read<Int>(Day(20)).value
    private val file = EncryptedFile(contents)

    override fun part1(): Long {
        return file.decrypt()
    }

    override fun part2(): Long {
        return file.decrypt(quantity = 10, decryptionKey = 811589153)
    }
}