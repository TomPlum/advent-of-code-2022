package io.github.tomplum.aoc.fs

class File(val name: String, val extension: String, val size: Long) {
    companion object {
        fun of(string: String, size: Long): File {
            return if (string.contains(".")) {
                val parts = string.split(".")
                val name = parts[0]
                val extension = parts[1]
                File(name, extension, size)
            } else {
                File(string, "", size)
            }
        }
    }

    override fun toString(): String {
        return "- $name.$extension (file, size=$size)"
    }
}