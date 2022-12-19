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

/*            val id = parts[0].removePrefix("Blueprint ").removeSuffix(":").toInt()
            val oreRobotCost = parts[1].removePrefix("Each ore robot costs ").removeSuffix(" ore.").toInt()
            val clayRobotCost = parts[1].removePrefix("Each clay robot costs ").removeSuffix(" ore.").toInt()

            val obsidianRobotParts = parts[2].split(" and ")
            val obsidianRobotOreCost = obsidianRobotParts[0].removePrefix("Each obsidian robot costs ").removeSuffix(" ore").toInt()
            val obsidianRobotClayCost = obsidianRobotParts[1].removeSuffix(" clay.").toInt()
            val obsidianRobotCost = Pair(obsidianRobotOreCost, obsidianRobotClayCost)

            val geodeRobotParts = parts[2].split(" and ")
            val geodeRobotOreCost = geodeRobotParts[0].removePrefix("Each geode robot costs ").removeSuffix(" ore").toInt()
            val geodeRobotObsidianCost = geodeRobotParts[1].removeSuffix(" obsidian.").toInt()
            val geodeRobotCost = Pair(geodeRobotOreCost, geodeRobotObsidianCost)*/

            val id = parts[1].removeSuffix(":").toInt()
            val oreRobotCost = parts[6].toInt()
            val clayRobotCost = parts[12].toInt()
            val obsidianRobotCost = Pair(parts[18].toInt(), parts[21].toInt())
            val geodeRobotCost = Pair(parts[27].toInt(), parts[30].toInt())

            return Blueprint(id, oreRobotCost, clayRobotCost, obsidianRobotCost, geodeRobotCost)
        }
    }

    fun canAffordObsidianRobot(ore: Int, clay: Int): Boolean {
        return ore >= obsidianRobotCost.first && clay >= obsidianRobotCost.second
    }

    fun canAffordGeodeCrackingRobot(ore: Int, obsidian: Int): Boolean {
        return ore >= geodeRobotCost.first && obsidian >= geodeRobotCost.second
    }
}