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

    fun findDirectory(path: String, dir: Directory = this): Directory? {
        if (path == dir.name) {
            return dir
        } else {
            dir.directories.forEach { subDir ->
                val found = findDirectory(path, subDir)
                if (found != null) {
                    return found
                }
            }
        }
        return null
    }

    fun findChildren(): List<Directory> {
        return directories + directories.flatMap { dir -> dir.findChildren() }
    }

    fun getSize(): Long {
        return files.sumOf { file -> file.size } + directories.sumOf { dir -> dir.getSize() }
    }

    private fun getSizeRecursively(): Long {
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

    fun print(indent: Int = 0): String {
        val s = StringBuilder("")
        val indentString = (1..indent).joinToString("") { " " }
        directories.forEach { dir ->
            s.append("$indentString$dir\n")
            s.append(dir.print(indent + 2))
        }
        files.forEach { file ->
            s.append("$indentString$file\n")
        }
        return s.toString()
    }

    override fun toString(): String {
       return "- $name (dir)"
    }
}