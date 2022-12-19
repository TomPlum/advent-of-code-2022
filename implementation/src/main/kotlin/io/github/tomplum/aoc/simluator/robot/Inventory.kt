package io.github.tomplum.aoc.simluator.robot

import io.github.tomplum.libs.extensions.nthBinomialCoefficient

data class Inventory(
    var ore: Int,
    private var oreCollectingRobots: Int,
    var clay: Int,
    private var clayCollectingRobots: Int,
    var obsidian: Int,
    private var obsidianCollectingRobots: Int,
    var openGeodes: Int,
    private var geodeCrackingRobots: Int
) {
    companion object {
        fun fromScratch(): Inventory {
            return Inventory(0, 1, 0, 0, 0, 0, 0, 0)
        }
    }

    fun createGeodeCrackingRobot(oreCost: Int, obsidianCost: Int): Inventory {
        val state = this.copy()
        state.ore = ore - oreCost
        state.obsidian = obsidian - obsidianCost

        state.collect()

        state.geodeCrackingRobots += 1

        return state
    }

    fun createObsidianCollectingRobot(oreCost: Int, clayCost: Int): Inventory {
        val state = this.copy()
        state.ore = ore - oreCost
        state.clay = clay - clayCost

        state.collect()

        state.obsidianCollectingRobots += 1

        return state
    }

    fun createClayCollectionRobot(oreCost: Int): Inventory {
        val state = this.copy()
        state.ore = ore - oreCost

        state.collect()

        state.clayCollectingRobots += 1

        return state
    }

    fun createOreCollectionRobot(oreCost: Int): Inventory {
        val state = this.copy()
        state.ore = ore - oreCost

        state.collect()

        state.oreCollectingRobots += 1

        return state
    }

    fun createNothing(): Inventory {
        val state = this.copy()
        state.collect()
        return state
    }

    fun producesEnoughOreToMakingEverything(blueprint: Blueprint): Boolean {
        return oreCollectingRobots >= blueprint.maxOreCost
    }

    fun producesEnoughClayToMakeObsidianRobots(blueprint: Blueprint): Boolean {
        return clayCollectingRobots >= blueprint.maxClayCost
    }

    fun producesEnoughObsidianToMakeGeodeRobots(blueprint: Blueprint): Boolean {
        return obsidianCollectingRobots >= blueprint.maxObsidianCost
    }

    private fun collect() {
        ore += oreCollectingRobots
        clay += clayCollectingRobots
        obsidian += obsidianCollectingRobots
        openGeodes += geodeCrackingRobots
    }

    fun canAffordGeodeCrackingRobot(blueprint: Blueprint): Boolean {
        return blueprint.canAffordGeodeCrackingRobot(ore, obsidian)
    }

    fun canAffordObsidianRobot(blueprint: Blueprint): Boolean {
        return blueprint.canAffordObsidianRobot(ore, clay)
    }

    fun canAffordClayRobot(blueprint: Blueprint): Boolean {
        return blueprint.canAffordClayRobot(ore)
    }

    fun canAffordOreRobot(blueprint: Blueprint): Boolean {
        return blueprint.canAffordOreRobot(ore)
    }

    fun optimisticGeodePrediction(timeRemaining: Int): Int {
        return (timeRemaining - 1).nthBinomialCoefficient() + openGeodes + geodeCrackingRobots + 1
    }

    fun key(): String {
        return "$ore$oreCollectingRobots$clay$clayCollectingRobots$obsidian$obsidianCollectingRobots$openGeodes$geodeCrackingRobots"
    }
}