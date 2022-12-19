package io.github.tomplum.aoc.simluator.robot

import io.github.tomplum.libs.logging.AdventLogger

class OreCollectingRobotSimulator(data: List<String>) {

    private val blueprints = data.map { info -> Blueprint.fromString(info) }

    private val cache = mutableMapOf<String, List<Pair<Int, Int>>>()

    fun simulate(): Int {
        val start = CollectionState(0, 1, 0, 0, 0, 0, 0, 0)
        return blueprints.asSequence().map { blueprint -> recurse(blueprint, 1, start)
            .maxBy { (_, geodes) -> geodes } }
            .sumOf { (id, geodes) -> id * geodes }
    }

    private fun recurse(blueprint: Blueprint, minute: Int, state: CollectionState): List<Pair<Int, Int>> {
        if (minute <= 24) {
            //AdventLogger.debug("== Minute $minute ==")

            val decisionStates = mutableListOf<CollectionState>()

            // Resources are spent immediately on the factory when available
            if (blueprint.canAffordGeodeCrackingRobot(state.ore, state.obsidian)) {
                //AdventLogger.debug("Spend ${blueprint.geodeRobotCost} to start building a geode-cracking robot.")
                /*decisionStates += CollectionState(
                    ore = state.ore - blueprint.geodeRobotCost.first,
                    oreCollectingRobots = state.oreCollectingRobots,
                    clay = state.clay,
                    clayCollectingRobots = state.clayCollectingRobots,
                    obsidian = state.obsidian - blueprint.geodeRobotCost.second,
                    obsidianCollectingRobots = state.obsidianCollectingRobots,
                    openGeodes = state.openGeodes,
                    geodeCrackingRobots = state.geodeCrackingRobots + 1
                )*/

                decisionStates += state.getGeodeCrackingRobotPurchaseState(blueprint.geodeRobotCost.first, blueprint.geodeRobotCost.second)
            }

            if (blueprint.canAffordObsidianRobot(state.ore, state.clay)) {
                //AdventLogger.debug("Spend ${blueprint.obsidianRobotCost} to start building an obsidian-collecting robot.")
                /*decisionStates += CollectionState(
                    ore = state.ore - blueprint.obsidianRobotCost.first,
                    oreCollectingRobots = state.oreCollectingRobots,
                    clay = state.clay - blueprint.obsidianRobotCost.second,
                    clayCollectingRobots = state.clayCollectingRobots,
                    obsidian = state.obsidian,
                    obsidianCollectingRobots = state.obsidianCollectingRobots + 1,
                    openGeodes = state.openGeodes,
                    geodeCrackingRobots = state.geodeCrackingRobots
                )*/

                decisionStates += state.getObsidianCollectingRobotPurchaseState(blueprint.obsidianRobotCost.first, blueprint.obsidianRobotCost.second)
            }

            if (state.ore >= blueprint.clayRobotCost) {
                //AdventLogger.debug("Spend ${blueprint.clayRobotCost} to start building a clay-collecting robot.")
                /*decisionStates += CollectionState(
                    ore = state.ore - blueprint.clayRobotCost,
                    oreCollectingRobots = state.oreCollectingRobots,
                    clay = state.clay,
                    clayCollectingRobots = state.clayCollectingRobots + 1,
                    obsidian = state.obsidian,
                    obsidianCollectingRobots = state.obsidianCollectingRobots,
                    openGeodes = state.openGeodes,
                    geodeCrackingRobots = state.geodeCrackingRobots
                )*/
                decisionStates += state.getClayCollectingRobotPurchaseState(blueprint.clayRobotCost)
            }

/*            // Collect Ore
            val oreCollected = state.oreCollectingRobots
            state.ore += oreCollected
            //AdventLogger.debug("${state.oreCollectingRobots} ore-collecting robot(s) collects $oreCollected ore; you now have ${state.ore} ore.")

            // Collect Clay
            val clayCollected = state.clayCollectingRobots
            state.clay += clayCollected
            //if (clayCollected > 0) AdventLogger.debug("${state.clayCollectingRobots} clay-collecting robot(s) collects $clayCollected clay; you now have ${state.clay} clay.")

            // Collect Obsidian
            val obsidianCollected = state.obsidianCollectingRobots
            state.obsidian += obsidianCollected
            //if (obsidianCollected > 0) AdventLogger.debug("${state.obsidianCollectingRobots} clay-collecting robot(s) collects $obsidianCollected obsidian; you now have ${state.obsidian} obsidian.")

            // Crack Geodes
            val geodesOpened = state.geodeCrackingRobots
            state.openGeodes += geodesOpened
            //if (geodesOpened > 0) AdventLogger.debug("${state.geodeCrackingRobots} geode-cracking robot(s) crack $geodesOpened geodes; you now have ${state.openGeodes} open geodes.")

            decisionStates.map { state ->
                state.ore += oreCollected
                state.clay += clayCollected
                state.obsidian += obsidianCollected
                state.openGeodes += geodesOpened
            }*/

            state.collect()

            return (decisionStates + state).flatMap { outcome ->
                cache.getOrPut("${blueprint.id}${state.key()}") { recurse(blueprint, minute + 1, outcome) }
            }
        } else {
            val totalOpenGeodes = listOf(blueprint.id to state.openGeodes)
            cache["${blueprint.id}${state.key()}"] = totalOpenGeodes
            return totalOpenGeodes
        }
    }

    inner class CollectionState(
        var ore: Int,
        private var oreCollectingRobots: Int,
        var clay: Int,
        private var clayCollectingRobots: Int,
        var obsidian: Int,
        private var obsidianCollectingRobots: Int,
        var openGeodes: Int,
        private var geodeCrackingRobots: Int
    ) {
        fun getGeodeCrackingRobotPurchaseState(oreCost: Int, obsidianCost: Int): CollectionState {
            val state = CollectionState(
                ore = ore - oreCost,
                oreCollectingRobots = oreCollectingRobots,
                clay = clay,
                clayCollectingRobots = clayCollectingRobots,
                obsidian = obsidian - obsidianCost,
                obsidianCollectingRobots = obsidianCollectingRobots,
                openGeodes = openGeodes,
                geodeCrackingRobots = geodeCrackingRobots
            )

            state.collect()

            state.geodeCrackingRobots += 1

            return state
        }

        fun getObsidianCollectingRobotPurchaseState(oreCost: Int, clayCost: Int): CollectionState {
            val state = CollectionState(
                ore = ore - oreCost,
                oreCollectingRobots = oreCollectingRobots,
                clay = clay - clayCost,
                clayCollectingRobots = clayCollectingRobots,
                obsidian = obsidian,
                obsidianCollectingRobots = obsidianCollectingRobots,
                openGeodes = openGeodes,
                geodeCrackingRobots = geodeCrackingRobots
            )

            state.collect()

            state.obsidianCollectingRobots += 1

            return state
        }

        fun getClayCollectingRobotPurchaseState(oreCost: Int): CollectionState {
            val state = CollectionState(
                ore = ore - oreCost,
                oreCollectingRobots = oreCollectingRobots,
                clay = clay,
                clayCollectingRobots = clayCollectingRobots,
                obsidian = obsidian,
                obsidianCollectingRobots = obsidianCollectingRobots,
                openGeodes = openGeodes,
                geodeCrackingRobots = geodeCrackingRobots
            )

            state.collect()

            state.clayCollectingRobots += 1

            return state
        }

        fun collect() {
            ore += oreCollectingRobots
            clay += clayCollectingRobots
            obsidian += obsidianCollectingRobots
            openGeodes += geodeCrackingRobots
        }

        fun key(): String {
            return "$ore$oreCollectingRobots$clay$clayCollectingRobots$obsidian$obsidianCollectingRobots$openGeodes$geodeCrackingRobots"
        }
    }

    enum class RobotType {
        ORE_ROBOT,
        CLAY_ROBOT,
        OBSIDIAN_ROBOT,
        GEODE_ROBOT
    }
}