package io.github.tomplum.aoc.fs

import io.github.tomplum.libs.logging.AdventLogger

class FileSystem(private val terminalOutput: List<String>) {
    fun getTotalFileSizeFromDirsNoGreaterThan(maxDirFileSize: Long): Long {
        val root = Directory.fromName("/")
        var lastTargetDirectoryName = "/"
        var targetDirectoryName = "/"
        var isListing = false

        terminalOutput.forEach { line ->
            if (line.startsWith("$")) {
                when(line.substring(2, 4)) {
                    "cd" -> {
                        isListing = false
                        val command = ChangeDirectory(line)
                        val newTargetDirName = command.getTargetDir()
                        if (newTargetDirName == "..") {
                            val tempCurrentDir = targetDirectoryName
                            targetDirectoryName = lastTargetDirectoryName
                            lastTargetDirectoryName = tempCurrentDir
                        } else {
                            lastTargetDirectoryName = targetDirectoryName
                            targetDirectoryName = newTargetDirName
                        }
                    }
                    "ls" -> {
                        isListing = true
                        return@forEach
                    }
                }
            }

            if (isListing) {
                val targetDirectory = root.findDirectory(targetDirectoryName)

                if (line.startsWith("dir")) {
                    val directoryName = line.split("dir ")[1].trim()
                    val newDirectory = Directory.fromName(directoryName)
                    targetDirectory?.addSubDirectory(newDirectory)
                } else {
                    val fileDetails = line.split(" ")
                    val fileSize = fileDetails[0].toLong()
                    val fileName = fileDetails[1].trim()
                    val file = File.of(fileName, fileSize)
                    targetDirectory?.addFile(file)
                }
            }
        }

        AdventLogger.debug(root)

        return root.getSumThing(maxDirFileSize).sum()
    }
}