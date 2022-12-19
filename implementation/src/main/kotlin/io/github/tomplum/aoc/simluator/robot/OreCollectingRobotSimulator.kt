package io.github.tomplum.aoc.simluator.robot

import io.github.tomplum.libs.logging.AdventLogger

class OreCollectingRobotSimulator(data: List<String>) {

    private val blueprints = data.map { info -> Blueprint.fromString(info) }

    fun simulate(): Int {
        val result = blueprints.map { blueprint ->
            var minute = 1

            var ore = 0
            var oreCollectingRobots = 1

            var clay = 0
            var clayCollectingRobots = 0

            var obsidian = 0
            var obsidianCollectingRobots = 0

            var openGeodes = 0
            var geodeCrackingRobots = 0

            while(minute <= 24) {
                AdventLogger.debug("== Minute $minute ==")

                val factory = mutableListOf<RobotType>()

                // Resources are spent immediately on the factory when available
                if (blueprint.canAffordGeodeCrackingRobot(ore, obsidian)) {
                    AdventLogger.debug("Spend ${blueprint.geodeRobotCost} to start building a geode-cracking robot.")
                    ore -= blueprint.geodeRobotCost.first
                    obsidian -= blueprint.geodeRobotCost.second
                    factory += RobotType.GEODE_ROBOT
                }

                if (blueprint.canAffordObsidianRobot(ore, clay)) {
                    AdventLogger.debug("Spend ${blueprint.obsidianRobotCost} to start building an obsidian-collecting robot.")
                    ore -= blueprint.obsidianRobotCost.first
                    clay -= blueprint.obsidianRobotCost.second
                    factory += RobotType.OBSIDIAN_ROBOT
                }

                if (ore >= blueprint.clayRobotCost) {
                    AdventLogger.debug("Spend ${blueprint.clayRobotCost} to start building a clay-collecting robot.")
                    ore -= blueprint.clayRobotCost
                    factory += RobotType.CLAY_ROBOT
                }

                // Collect Ore
                val oreCollected = oreCollectingRobots
                ore += oreCollected
                AdventLogger.debug("$oreCollectingRobots ore-collecting robot(s) collects $oreCollected ore; you now have $ore ore.")

                // Collect Clay
                val clayCollected = clayCollectingRobots
                clay += clayCollected
                if (clayCollected > 0) AdventLogger.debug("$clayCollectingRobots clay-collecting robot(s) collects $clayCollected clay; you now have $clay clay.")

                // Collect Obsidian
                val obsidianCollected = obsidianCollectingRobots
                obsidian += obsidianCollected
                if (obsidianCollected > 0) AdventLogger.debug("$obsidianCollectingRobots clay-collecting robot(s) collects $obsidianCollected obsidian; you now have $obsidian obsidian.")

                // Crack Geodes
                val geodesOpened = geodeCrackingRobots
                openGeodes += geodesOpened
                if (geodesOpened > 0) AdventLogger.debug("$geodeCrackingRobots geode-cracking robot(s) crack $geodesOpened geodes; you now have $openGeodes open geodes.")

                // The minute is over, the factory finishes making whatever is in-progress
                factory.forEach { robot -> when(robot) {
                    RobotType.CLAY_ROBOT -> {
                        clayCollectingRobots += 1
                        AdventLogger.debug("The new clay-collecting robot is ready; you now have $clayCollectingRobots of them.")
                    }
                    RobotType.ORE_ROBOT -> {
                        oreCollectingRobots += 1
                        AdventLogger.debug("The new ore-collecting robot is ready; you now have $oreCollectingRobots of them.")
                    }
                    RobotType.OBSIDIAN_ROBOT -> {
                        obsidianCollectingRobots += 1
                        AdventLogger.debug("The new obsidian-collecting robot is ready; you now have $obsidianCollectingRobots of them.")
                    }
                    RobotType.GEODE_ROBOT -> {
                        geodeCrackingRobots += 1
                        AdventLogger.debug("The new geode-cracking robot is ready; you now have $geodeCrackingRobots of them.")
                    }
                }}

                minute += 1

                AdventLogger.debug("\n")
            }

            blueprint.id to openGeodes
        }

        return result.sumOf { (id, geodes) -> id * geodes }
    }

    enum class RobotType {
        ORE_ROBOT,
        CLAY_ROBOT,
        OBSIDIAN_ROBOT,
        GEODE_ROBOT
    }
}