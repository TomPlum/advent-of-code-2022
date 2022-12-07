package io.github.tomplum.aoc.fs

import io.github.tomplum.libs.logging.AdventLogger
import java.util.Stack

class FileSystem(private val terminalOutput: List<String>) {
    fun getTotalFileSizeFromDirsNoGreaterThan(maxDirFileSize: Long): Long {
        val root = parseTerminalOutput()
        val children = root.findChildren()
        val distinct = children.distinctBy { it.name }
        val duplicates = children.groupingBy { it.name }.eachCount().filter { it.value > 1 }
        return root.findChildren()
            .sumOf { dir ->
                val size = dir.getSize()
                if (size <= maxDirFileSize) size else 0
            }
    }

    fun parseTerminalOutput(): Directory {
        val root = Directory.of("/", null)
        val breadCrumbs = Stack<String>()
        var activeDirectory = root
        var isListing = false

        terminalOutput.forEach { line ->
            val logPrefix = "$line ${(1..(50-line.length)).joinToString("") { " " } } -"
                if (line.startsWith("$")) {
                when (line.substring(2, 4)) {
                    "cd" -> {
                        isListing = false
                        val newTargetDirName = ChangeDirectory(line).getTargetDir()
                        if (newTargetDirName == "..") {
                            breadCrumbs.pop()
                            activeDirectory = activeDirectory.parentDir ?: throw IllegalStateException("Can't go up from root directory.")
                            AdventLogger.debug("$logPrefix going up a dir")
                        } else {
                            breadCrumbs.push(newTargetDirName)
                            if (activeDirectory.directories.find { dir -> dir.name == newTargetDirName } == null) {
                                activeDirectory.addSubDirectory(Directory.of(newTargetDirName, activeDirectory))
                            }
                            activeDirectory = activeDirectory.directories.find { dir -> dir.name == newTargetDirName }!!
                            AdventLogger.debug("$logPrefix going into $newTargetDirName")
                        }
                    }
                    "ls" -> {
                        isListing = true
                        AdventLogger.debug("$logPrefix listing...")
                        return@forEach
                    }
                }
            }

            if (isListing) {
               /* val targetDir = breadCrumbs.peek()
                val targetDirectory = root.findDirectory(targetDir, )
                    ?: throw IllegalArgumentException("Cannot find directory with path [$targetDir]")*/

                if (line.startsWith("dir")) {
                    val directoryName = line.removePrefix("dir ").trim()
                    if (activeDirectory.directories.find { dir -> dir.name == directoryName } == null) {
                        val newDirectory = Directory.of(directoryName, activeDirectory)
                        activeDirectory.addSubDirectory(newDirectory)
                    }
                    AdventLogger.debug("$logPrefix Adding $directoryName inside ${activeDirectory.name}")
                } else {
                    val fileDetails = line.split(" ")
                    val fileSize = fileDetails[0].toLong()
                    val fileName = fileDetails[1].trim()
                    val file = File(fileName, fileSize)
                    activeDirectory.addFile(file)
                    AdventLogger.debug("$logPrefix Adding file $fileName with size $fileSize inside ${activeDirectory.name}")
                }
            }
        }

        AdventLogger.debug(root.print())

        return root
    }
}