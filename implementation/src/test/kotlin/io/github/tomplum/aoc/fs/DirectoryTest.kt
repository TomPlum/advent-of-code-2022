package io.github.tomplum.aoc.fs

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DirectoryTest {
    @Nested
    inner class FindDirectory {
        @Test
        fun directDescendant() {
            val root = Directory.fromName("/")
            root.addSubDirectory(Directory.fromName("a"))

            val found = root.findDirectory("a")

            assertThat(found?.name).isEqualTo("a")
        }

        @Test
        fun nestedChild() {
            val root = Directory.fromName("/")
            val immediateChild = Directory.fromName("a")
            root.addSubDirectory(immediateChild)
            immediateChild.addSubDirectory(Directory.fromName("b"))

            val found = root.findDirectory("b")

            assertThat(found?.name).isEqualTo("b")
        }
    }

    @Nested
    inner class GetSize {
        private val terminalOutput = TestInputReader.read<String>("day7/example.txt").value
        private val fs = FileSystem(terminalOutput)
        private val root = fs.parseTerminalOutput()

        @Test
        fun exampleOneDirectoryE() {
            assertThat(root.findDirectory("e")?.getSize()).isEqualTo(584)
        }

        @Test
        fun exampleOneDirectoryA() {
            assertThat(root.findDirectory("a")?.getSize()).isEqualTo(94853)
        }

        @Test
        fun exampleOneDirectoryD() {
            assertThat(root.findDirectory("d")?.getSize()).isEqualTo(24933642)
        }

        @Test
        fun exampleOneRootDirectory() {
            assertThat(root.getSize()).isEqualTo(48381165)
        }
    }
}