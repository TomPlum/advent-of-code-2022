package io.github.tomplum.aoc.fs

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Test

class FileSystemTest {
    private val terminalOutput = TestInputReader.read<String>("day7/example.txt").value
    private val fileSystem = FileSystem(terminalOutput)

    @Test
    fun examplePartOne() {
        assertThat(fileSystem.getTotalFileSizeFromDirsNoGreaterThan(100000)).isEqualTo(95437)
    }
}