package io.github.tomplum.aoc.fs

class ChangeDirectory(private val command: String) {
    fun getTargetDir(): String {
        return command.removePrefix("\$ cd ").trim()
    }
}