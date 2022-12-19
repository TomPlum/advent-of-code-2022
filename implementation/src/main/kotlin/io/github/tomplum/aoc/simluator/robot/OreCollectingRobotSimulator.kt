package io.github.tomplum.aoc.simluator.robot

class OreCollectingRobotSimulator(data: List<String>) {

    private val blueprints = data.map { info -> Blueprint.fromString(info) }

    private val cache = mutableMapOf<String, Int>()
    private var maxGeodesFound = 0

    /**
     * 549 is too low for the solution
     */
    fun simulate(): Int {
        val start = InventoryState(0, 1, 0, 0, 0, 0, 0, 0)
        val geodes = blueprints.map { blueprint ->
            blueprint.id to calculateGeodesSmashed(blueprint, 24, start)
        }
        return geodes.sumOf { (id, geodes) -> id * geodes }
    }

    private fun calculateGeodesSmashed(blueprint: Blueprint, minute: Int, inventory: InventoryState): Int {
        if (minute == 0) {
            maxGeodesFound = maxOf(maxGeodesFound, inventory.openGeodes)
            return inventory.openGeodes
        }

        if (inventory.canAffordGeodeCrackingRobot(blueprint)) {
            val updatedInventory = inventory.createGeodeCrackingRobot(
                blueprint.geodeRobotCost.first,
                blueprint.geodeRobotCost.second
            )
            return searchDeeper(blueprint, minute, updatedInventory)
        } else {
            val hasEnoughObsidian = inventory.producesEnoughObsidianToMakeGeodeRobots(blueprint)
            if (!hasEnoughObsidian && inventory.canAffordObsidianRobot(blueprint)) {
                val updatedInventory = inventory.createObsidianCollectingRobot(
                    blueprint.obsidianRobotCost.first,
                    blueprint.obsidianRobotCost.second
                )
                maxGeodesFound = searchDeeper(blueprint, minute, updatedInventory)
            }

            val hasEnoughClay = inventory.producesEnoughClayToMakeObsidianRobots(blueprint)
            if (!hasEnoughClay && inventory.canAffordClayRobot(blueprint)) {
                val updatedInventory = inventory.createClayCollectionRobot(blueprint.clayRobotCost)
                maxGeodesFound = searchDeeper(blueprint, minute, updatedInventory)
            }

            val hasEnoughOre = inventory.producesEnoughOreToMakingEverything(blueprint)
            if (!hasEnoughOre && inventory.canAffordOreRobot(blueprint)) {
                val updatedInventory = inventory.createOreCollectionRobot(blueprint.oreRobotCost)
                maxGeodesFound = searchDeeper(blueprint, minute, updatedInventory)
            }

            if (!hasEnoughOre || !hasEnoughClay || !hasEnoughObsidian) {
                val updatedInventory = inventory.createNothing()
                maxGeodesFound = searchDeeper(blueprint, minute, updatedInventory)
            }
        }

        return maxGeodesFound
    }

    private fun searchDeeper(blueprint: Blueprint, minute: Int, inventory: InventoryState): Int {
        val geodes = cache.getOrPut("${blueprint.id}${inventory.key()}") {
            calculateGeodesSmashed(blueprint, minute - 1, inventory)
        }
        return maxOf(maxGeodesFound, geodes)
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