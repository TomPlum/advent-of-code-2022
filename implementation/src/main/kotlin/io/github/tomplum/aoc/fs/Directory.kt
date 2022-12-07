package io.github.tomplum.aoc.fs

data class Directory(val name: String, val files: MutableList<File>, val parentDir: Directory?, val directories: MutableList<Directory>) {

    companion object {
        fun of(name: String, parentDir: Directory?): Directory {
            return Directory(name, mutableListOf(), parentDir, mutableListOf())
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

    fun print(indent: Int = 0): String {
        val s = StringBuilder("")
        var indentTarget = indent
        if (indent == 0) {
            s.append("\n- / (dir)\n")
            indentTarget += 2
        }
        val indentString = (1..indentTarget).joinToString("") { " " }
        directories.forEach { dir ->
            s.append("$indentString$dir\n")
            s.append(dir.print(indentTarget + 2))
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