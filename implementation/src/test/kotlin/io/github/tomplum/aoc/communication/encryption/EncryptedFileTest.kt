package io.github.tomplum.aoc.communication.encryption

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class EncryptedFileTest {
    private val contents = TestInputReader.read<Int>("day20/example.txt").value
    private val file = EncryptedFile(contents)

    @Test
    fun examplePartOne() {
        assertThat(file.decrypt()).isEqualTo(3)
    }

    @Test
    fun examplePartTwo() {
        assertThat(file.decrypt(10, 811589153)).isEqualTo(1623178306)
    }
}