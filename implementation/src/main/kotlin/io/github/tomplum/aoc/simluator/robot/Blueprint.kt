package io.github.tomplum.aoc.simluator.robot

data class Blueprint(
    val id: Int,
    val oreRobotCost: Int,
    val clayRobotCost: Int,
    val obsidianRobotCost: Pair<Int, Int>,
    val geodeRobotCost: Pair<Int, Int>
) {
    companion object {
        fun fromString(value: String): Blueprint {
            val parts = value.trim().split(" ")

            val id = parts[1].removeSuffix(":").toInt()
            val oreRobotCost = parts[6].toInt()
            val clayRobotCost = parts[12].toInt()
            val obsidianRobotCost = Pair(parts[18].toInt(), parts[21].toInt())
            val geodeRobotCost = Pair(parts[27].toInt(), parts[30].toInt())

            return Blueprint(id, oreRobotCost, clayRobotCost, obsidianRobotCost, geodeRobotCost)
        }
    }

    val maxOreCost = listOf(oreRobotCost, clayRobotCost, obsidianRobotCost.first, geodeRobotCost.first).max()
    val maxClayCost = obsidianRobotCost.second
    val maxObsidianCost = geodeRobotCost.second

    fun canAffordObsidianRobot(ore: Int, clay: Int): Boolean {
        return ore >= obsidianRobotCost.first && clay >= obsidianRobotCost.second
    }

    fun canAffordGeodeCrackingRobot(ore: Int, obsidian: Int): Boolean {
        return ore >= geodeRobotCost.first && obsidian >= geodeRobotCost.second
    }

    fun canAffordClayRobot(ore: Int): Boolean {
        return ore >= clayRobotCost
    }

    fun canAffordOreRobot(ore: Int): Boolean {
        return ore >= oreRobotCost
    }
}