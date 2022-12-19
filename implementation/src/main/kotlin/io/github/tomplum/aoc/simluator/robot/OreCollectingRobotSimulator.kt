package io.github.tomplum.aoc.simluator.robot

import io.github.tomplum.libs.extensions.nthBinomialCoefficient

class OreCollectingRobotSimulator(data: List<String>) {

    private val blueprints = data.map { info -> Blueprint.fromString(info) }

    private val cache = mutableMapOf<String, List<Int>>()
    private var maxGeodesFound = 0

    /**
     * 549 is too low for the solution
     */
    fun simulate(): Int {
        val start = InventoryState(0, 1, 0, 0, 0, 0, 0, 0)
        val geodes = blueprints.map { blueprint ->
            blueprint.id to calculateGeodesSmashed(blueprint, 1, start).max()
        }
        return geodes.sumOf { (id, geodes) -> id * geodes }
    }

    private fun calculateGeodesSmashed(blueprint: Blueprint, minute: Int, inventory: InventoryState): List<Int> {
        if (minute <= 24) {
            val states = mutableListOf<InventoryState>()

        /*    val timeRemaining = 24 - minute
            val optimisticGeodePrediction = (timeRemaining - 1).nthBinomialCoefficient()
            if (optimisticGeodePrediction <= maxGeodesFound) {
                return listOf(inventory.openGeodes)
            }*/

            if (inventory.canAffordGeodeCrackingRobot(blueprint)) {
                states += inventory.createGeodeCrackingRobot(
                    blueprint.geodeRobotCost.first,
                    blueprint.geodeRobotCost.second
                )
            } else {
                val hasEnoughObsidian = inventory.producesEnoughObsidianToMakeGeodeRobots(blueprint)
                if (!hasEnoughObsidian && inventory.canAffordObsidianRobot(blueprint)) {
                    states += inventory.createObsidianCollectingRobot(
                        blueprint.obsidianRobotCost.first,
                        blueprint.obsidianRobotCost.second
                    )
                }

                val hasEnoughClay = inventory.producesEnoughClayToMakeObsidianRobots(blueprint)
                if (!hasEnoughClay && inventory.canAffordClayRobot(blueprint)) {
                    states += inventory.createClayCollectionRobot(blueprint.clayRobotCost)
                }

                val hasEnoughOre = inventory.producesEnoughOreToMakingEverything(blueprint)
                if (!hasEnoughOre && inventory.canAffordOreRobot(blueprint)) {
                    states += inventory.createOreCollectionRobot(blueprint.oreRobotCost)
                }

                if ((!hasEnoughOre || !hasEnoughClay || !hasEnoughObsidian) && states.isEmpty()) {
                    states += inventory.createNothing()
                }
            }

            return states.flatMap { nextState ->
                cache.getOrPut("${blueprint.id}${nextState.key()}") {
                    calculateGeodesSmashed(blueprint, minute + 1, nextState)
                }
            }
        } else {
            val geodes = inventory.openGeodes
            if (geodes > maxGeodesFound) maxGeodesFound = geodes
            return listOf(geodes)
        }
    }

    data class InventoryState(
        var ore: Int,
        private var oreCollectingRobots: Int,
        var clay: Int,
        private var clayCollectingRobots: Int,
        var obsidian: Int,
        private var obsidianCollectingRobots: Int,
        var openGeodes: Int,
        private var geodeCrackingRobots: Int
    ) {
        fun createGeodeCrackingRobot(oreCost: Int, obsidianCost: Int): InventoryState {
            val state = this.copy()
            state.ore = ore - oreCost
            state.obsidian = obsidian - obsidianCost

            state.collect()

            state.geodeCrackingRobots += 1

            return state
        }

        fun createObsidianCollectingRobot(oreCost: Int, clayCost: Int): InventoryState {
            val state = this.copy()
            state.ore = ore - oreCost
            state.clay = clay - clayCost

            state.collect()

            state.obsidianCollectingRobots += 1

            return state
        }

        fun createClayCollectionRobot(oreCost: Int): InventoryState {
            val state = this.copy()
            state.ore = ore - oreCost

            state.collect()

            state.clayCollectingRobots += 1

            return state
        }

        fun createOreCollectionRobot(oreCost: Int): InventoryState {
            val state = this.copy()
            state.ore = ore - oreCost

            state.collect()

            state.oreCollectingRobots += 1

            return state
        }

        fun createNothing(): InventoryState {
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

        fun key(): String {
            return "$ore$oreCollectingRobots$clay$clayCollectingRobots$obsidian$obsidianCollectingRobots$openGeodes$geodeCrackingRobots"
        }
    }
}