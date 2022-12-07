package io.github.tomplum.aoc.fs

class File(private val name: String, val size: Long) {
    override fun toString(): String {
        return "- $name (file, size=$size)"
    }
}