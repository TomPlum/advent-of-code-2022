package io.github.tomplum.aoc.fs

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.extracting
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DirectoryTest {
    @Nested
    inner class GetSize {
        private val terminalOutput = TestInputReader.read<String>("day7/example.txt").value
        private val fs = FileSystem(terminalOutput)
        private val root = fs.parseTerminalOutput()

        @Test
        fun exampleOneDirectoryE() {
            assertThat(root.findChildren().find { it.name == "e" }?.getSize()).isEqualTo(584)
        }

        @Test
        fun exampleOneDirectoryA() {
            assertThat(root.findChildren().find { it.name == "a" }?.getSize()).isEqualTo(94853)
        }

        @Test
        fun exampleOneDirectoryD() {
            assertThat(root.findChildren().find { it.name == "d" }?.getSize()).isEqualTo(24933642)
        }

        @Test
        fun exampleOneRootDirectory() {
            assertThat(root.getSize()).isEqualTo(48381165)
        }
    }

    @Nested
    inner class FindChildren {
        private val terminalOutput = TestInputReader.read<String>("day7/example.txt").value
        private val fs = FileSystem(terminalOutput)
        private val root = fs.parseTerminalOutput()

        @Test
        fun exampleOneStructure() {
            assertThat(root.findChildren()).extracting { dir -> dir.name }.containsExactlyInAnyOrder("/", "a", "e", "d")
        }
    }
}