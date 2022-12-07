package io.github.tomplum.aoc.fs

data class Directory(val name: String, val files: MutableList<File>, val directories: MutableList<Directory>) {

    companion object {
        fun fromName(name: String): Directory {
            return Directory(name, mutableListOf(), mutableListOf())
        }
    }

    fun addFile(file: File) {
        files.add(file)
    }

    fun addSubDirectory(directory: Directory) {
        directories.add(directory)
    }

    fun findDirectory(path: String): Directory? {
        return if (path == name) {
            this
        } else {
            directories.find { subDir ->
                return subDir.findDirectory(path)
            }
        }
    }

    fun getSizeRecursively(): Long {
        return files.sumOf { file -> file.size } + directories.sumOf { dir -> dir.getSizeRecursively() }
    }

    fun getSumThing(maxSize: Long, sizes: MutableList<Long> = mutableListOf()): MutableList<Long> {
        val sizeRecursively = getSizeRecursively()
        if (sizeRecursively <= maxSize) {
            sizes.add(sizeRecursively)
        }

        directories.map { dir ->
            dir.getSumThing(maxSize, sizes)
        }

        return sizes
    }

    override fun toString(): String {
        var indent = 0
        val s = StringBuilder("- $name (dir)").append("\n")
        directories.forEach { dir ->
            indent += 2
            s.append("- ${dir.name} (dir)\n")
            dir.directories.forEach { subDir -> s.append(subDir.toString()) }
            dir.files.forEach { file ->
                s.append("- ${file.name} (file, size=${file.size})\n")
            }
        }
        return s.toString()
    }
}