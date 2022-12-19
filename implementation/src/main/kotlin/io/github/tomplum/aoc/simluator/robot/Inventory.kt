package io.github.tomplum.aoc.simluator.robot

import io.github.tomplum.libs.extensions.nthBinomialCoefficient

/**
 * Tracks the materials and robots of an [OreCollectingRobotSimulator].
 *
 * All robots can perform one of their operations per minute.
 *
 * @param ore The quantity of ore remaining
 * @param oreCollectingRobots The quantity of ore-collecting robots in operation
 * @param clay The quantity of clay remaining
 * @param clayCollectingRobots The quantity of clay-collecting robots in operation
 * @param obsidian The quantity of obsidian remaining
 * @param obsidianCollectingRobots The quantity of obsidian-collecting robots in operation
 * @param openGeodes The quantity of geodes that have been smashed open
 * @param geodeCrackingRobots The quantity of geode-smashing robots in operation
 */
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
        /**
         * Produces an [Inventory] instance that
         * represents the starting state of a simulation.
         * @return The inventory instance
         */
        fun fromScratch(): Inventory {
            return Inventory(0, 1, 0, 0, 0, 0, 0, 0)
        }
    }

    /**
     * Produces a new state of [Inventory] with
     * updated materials and robot quantities after
     * having created a geode-cracking robot.
     *
     * @param oreCost The quantity of ore required
     * @param obsidianCost The quantity of obsidian required
     *
     * @return The updated [Inventory]
     */
    fun createGeodeCrackingRobot(oreCost: Int, obsidianCost: Int): Inventory {
        val state = this.copy()
        state.ore = ore - oreCost
        state.obsidian = obsidian - obsidianCost

        state.collect()

        state.geodeCrackingRobots += 1

        return state
    }

    /**
     * Produces a new state of [Inventory] with
     * updated materials and robot quantities after
     * having created an obsidian-collecting robot.
     *
     * @param oreCost The quantity of ore required
     * @param clayCost The quantity of clay required
     *
     * @return The updated [Inventory]
     */
    fun createObsidianCollectingRobot(oreCost: Int, clayCost: Int): Inventory {
        val state = this.copy()
        state.ore = ore - oreCost
        state.clay = clay - clayCost

        state.collect()

        state.obsidianCollectingRobots += 1

        return state
    }

    /**
     * Produces a new state of [Inventory] with
     * updated materials and robot quantities after
     * having created a clay-collecting robot.
     *
     * @param oreCost The quantity of ore required
     *
     * @return The updated [Inventory]
     */
    fun createClayCollectionRobot(oreCost: Int): Inventory {
        val state = this.copy()
        state.ore = ore - oreCost

        state.collect()

        state.clayCollectingRobots += 1

        return state
    }

    /**
     * Produces a new state of [Inventory] with
     * updated materials and robot quantities after
     * having created a ore-collecting robot.
     *
     * @param oreCost The quantity of ore required
     *
     * @return The updated [Inventory]
     */
    fun createOreCollectionRobot(oreCost: Int): Inventory {
        val state = this.copy()
        state.ore = ore - oreCost

        state.collect()

        state.oreCollectingRobots += 1

        return state
    }

    /**
     * Produces a new state of [Inventory] with
     * updated materials and robot quantities after
     * having created no robots, but collecting resources
     * with the existing robots.
     *
     * @return The updated [Inventory]
     */
    fun createNothing(): Inventory {
        val state = this.copy()
        state.collect()
        return state
    }

    /**
     * Checks if the current [oreCollectingRobots] can
     * collect enough ore in any given minute to be
     * able to make any of the available robots as per
     * the given [blueprint] specification.
     *
     * @param blueprint The blueprint to check against
     * @return true if produces enough ore, else false
     */
    fun producesEnoughOreToMakingEverything(blueprint: Blueprint): Boolean {
        return oreCollectingRobots >= blueprint.maxOreCost
    }

    /**
     * Checks if the current [clayCollectingRobots] can
     * collect enough clay in any given minute to be
     * able to make any of the available robots as per
     * the given [blueprint] specification.
     *
     * @param blueprint The blueprint to check against
     * @return true if produces enough clay, else false
     */
    fun producesEnoughClayToMakeObsidianRobots(blueprint: Blueprint): Boolean {
        return clayCollectingRobots >= blueprint.maxClayCost
    }

    /**
     * Checks if the current [obsidianCollectingRobots] can
     * collect enough obsidian in any given minute to be
     * able to make any of the available robots as per
     * the given [blueprint] specification.
     *
     * @param blueprint The blueprint to check against
     * @return true if produces enough obsidian, else false
     */
    fun producesEnoughObsidianToMakeGeodeRobots(blueprint: Blueprint): Boolean {
        return obsidianCollectingRobots >= blueprint.maxObsidianCost
    }

    /**
     * Updates the collected material quantities
     * of the current [Inventory] based on the number
     * of robots available to collect or smash.
     */
    private fun collect() {
        ore += oreCollectingRobots
        clay += clayCollectingRobots
        obsidian += obsidianCollectingRobots
        openGeodes += geodeCrackingRobots
    }

    /**
     * Checks if we can afford to produce another
     * geode-cracking robot at the factory based on the
     * available materials and the [blueprint] specification.
     *
     * @param blueprint The blueprint to check against
     * @return true if it can be afforded, else false
     */
    fun canAffordGeodeCrackingRobot(blueprint: Blueprint): Boolean {
        return blueprint.canAffordGeodeCrackingRobot(ore, obsidian)
    }

    /**
     * Checks if we can afford to produce another
     * obsidian-collecting robot at the factory based on the
     * available materials and the [blueprint] specification.
     *
     * @param blueprint The blueprint to check against
     * @return true if it can be afforded, else false
     */
    fun canAffordObsidianRobot(blueprint: Blueprint): Boolean {
        return blueprint.canAffordObsidianRobot(ore, clay)
    }

    /**
     * Checks if we can afford to produce another
     * clay-collecting robot at the factory based on the
     * available materials and the [blueprint] specification.
     *
     * @param blueprint The blueprint to check against
     * @return true if it can be afforded, else false
     */
    fun canAffordClayRobot(blueprint: Blueprint): Boolean {
        return blueprint.canAffordClayRobot(ore)
    }

    /**
     * Checks if we can afford to produce another
     * ore-collecting robot at the factory based on the
     * available materials and the [blueprint] specification.
     *
     * @param blueprint The blueprint to check against
     * @return true if it can be afforded, else false
     */
    fun canAffordOreRobot(blueprint: Blueprint): Boolean {
        return blueprint.canAffordOreRobot(ore)
    }

    /**
     * Predicts (optimistically) how many geodes could be cracked
     * open based on the [timeRemaining].
     *
     * We assume that in the best-case scenario, we can produce
     * one geode-cracking robot per minute remaining.
     *
     * This produces a triangular-number of geodes that could
     * be produced. Add this to the number already opened.
     *
     * @param timeRemaining The number of minutes remaining
     * @return The optimistic maximum number of geodes that can be opened
     */
    fun optimisticGeodePrediction(timeRemaining: Int): Int {
        return (timeRemaining - 1).nthBinomialCoefficient() + openGeodes + geodeCrackingRobots + 1
    }

    /**
     * A unique identifier for this inventory.
     * @return The unique ID
     */
    fun key(): String {
        return "$ore$oreCollectingRobots$clay$clayCollectingRobots$obsidian$obsidianCollectingRobots$openGeodes$geodeCrackingRobots"
    }
}