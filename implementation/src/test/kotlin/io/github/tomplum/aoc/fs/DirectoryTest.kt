package io.github.tomplum.aoc.fs

import assertk.assertThat
import assertk.assertions.isEqualTo
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
}