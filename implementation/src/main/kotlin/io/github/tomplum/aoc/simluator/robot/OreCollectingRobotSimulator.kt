package io.github.tomplum.aoc.simluator.robot

class OreCollectingRobotSimulator(data: List<String>) {

    private val blueprints = data.map { info -> Blueprint.fromString(info) }

    private val cache = mutableMapOf<String, List<Int>>()

    fun simulate(): Int {
        val start = InventoryState(0, 1, 0, 0, 0, 0, 0, 0)
        return blueprints.asSequence().map { blueprint ->
            blueprint.id to calculateGeodesSmashed(blueprint, 1, start).max()
        }.sumOf { (id, geodes) -> id * geodes }
    }

    private fun calculateGeodesSmashed(blueprint: Blueprint, minute: Int, inventory: InventoryState): List<Int> {
        if (minute <= 24) {
            val states = mutableListOf<InventoryState>()

            if (inventory.canAffordGeodeCrackingRobot(blueprint)) {
                states += inventory.createGeodeCrackingRobot(
                    blueprint.geodeRobotCost.first,
                    blueprint.geodeRobotCost.second
                )
            }

            if (inventory.canAffordObsidianRobot(blueprint)) {
                states += inventory.createObsidianCollectingRobot(
                    blueprint.obsidianRobotCost.first,
                    blueprint.obsidianRobotCost.second
                )
            }

            if (inventory.canAffordClayRobot(blueprint)) {
                states += inventory.createClayCollectionRobot(blueprint.clayRobotCost)
            }

            states += inventory.createNothing()

            return states.flatMap { nextState ->
                cache.getOrPut("${blueprint.id}${nextState.key()}") {
                    calculateGeodesSmashed(blueprint, minute + 1, nextState)
                }
            }
        } else {
            return listOf(inventory.openGeodes)
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

        fun createNothing(): InventoryState {
            val state = this.copy()
            state.collect()
            return state
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

        fun key(): String {
            return "$ore$oreCollectingRobots$clay$clayCollectingRobots$obsidian$obsidianCollectingRobots$openGeodes$geodeCrackingRobots"
        }
    }
}