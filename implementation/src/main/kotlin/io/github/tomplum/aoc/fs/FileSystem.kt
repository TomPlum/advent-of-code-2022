package io.github.tomplum.aoc.fs

class FileSystem(private val terminalOutput: List<String>) {
    fun getTotalFileSizeFromDirsNoGreaterThan(maxDirFileSize: Long): Long {
        val root = parseTerminalOutput()
        return root.findChildren()
            .sumOf { dir ->
                val size = dir.getSize()
                if (size <= maxDirFileSize) size else 0
            }
    }

    fun getSmallestDeletableDirectoryToFreeEnoughSpace(): Directory {
        val root = parseTerminalOutput()
        val diskSpaceUsed = root.getSize()
        val totalDiskSpace = 70000000
        val freeSpaceNeeded = 30000000
        val availableDiskSpace = totalDiskSpace - diskSpaceUsed
        val spaceNeededToBeFreed = freeSpaceNeeded - availableDiskSpace
        return root.findChildren()
            .filter { dir -> dir.getSize() >= spaceNeededToBeFreed }
            .minByOrNull { dir -> dir.getSize() }!!
    }

    fun parseTerminalOutput(): Directory {
        val root = Directory.of("/", null)
        var activeDirectory = root
        var isListing = false

        terminalOutput.forEach { line ->
            if (line.startsWith("$")) {
                when (line.substring(2, 4)) {
                    "cd" -> {
                        isListing = false
                        val newTargetDirName = ChangeDirectory(line).getTargetDir()
                        activeDirectory = if (newTargetDirName == "..") {
                            activeDirectory.parentDir ?: throw IllegalStateException("Can't go up from root directory.")
                        } else {
                            if (activeDirectory.directories.find { dir -> dir.name == newTargetDirName } == null) {
                                activeDirectory.addSubDirectory(Directory.of(newTargetDirName, activeDirectory))
                            }
                            activeDirectory.directories.find { dir -> dir.name == newTargetDirName }!!
                        }
                    }

                    "ls" -> {
                        isListing = true
                        return@forEach
                    }
                }
            }

            if (isListing) {
                if (line.startsWith("dir")) {
                    val directoryName = line.removePrefix("dir ").trim()
                    if (activeDirectory.directories.find { dir -> dir.name == directoryName } == null) {
                        val newDirectory = Directory.of(directoryName, activeDirectory)
                        activeDirectory.addSubDirectory(newDirectory)
                    }
                } else {
                    val fileDetails = line.split(" ")
                    val fileSize = fileDetails[0].toLong()
                    val fileName = fileDetails[1].trim()
                    val file = File(fileName, fileSize)
                    activeDirectory.addFile(file)
                }
            }
        }

        // AdventLogger.debug(root.print())

        return root
    }
}