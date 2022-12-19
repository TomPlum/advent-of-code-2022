package io.github.tomplum.aoc.simluator.robot

import io.github.tomplum.libs.extensions.product

class OreCollectingRobotSimulator(data: List<String>) {

    private val blueprints = data.map { info -> Blueprint.fromString(info) }

    private val cache = mutableMapOf<String, Int>()
    private var maxGeodesFound = 0

    /**
     * Simulates the mineral collection process for 24 minutes.
     * Uses the data from all the given [blueprints] to find
     * the maximum possible number of geodes that can be smashed
     * in the allotted time.
     *
     * Each blueprints' ID is multiplied by the maximum number of
     * geodes smashed using that blueprint, this is the score.
     *
     * @return The sum of the scores
     */
    fun simulate(): Int {
        val geodes = blueprints.asSequence().map { blueprint ->
            maxGeodesFound = 0
            cache.clear()
            blueprint.id to smashGeodes(blueprint, 24, Inventory.fromScratch())
        }
        return geodes.sumOf { (id, geodes) -> id * geodes }
    }

    /**
     * Simulates the mineral collection process for 32 minutes.
     * Uses the data from all the first three [blueprints] to find
     * the maximum possible number of geodes that can be smashed
     * in the allotted time.
     *
     * Each blueprints' ID is multiplied by the maximum number of
     * geodes smashed using that blueprint, this is the score.
     *
     * @return The sum of the scores
     */
    fun simulateWhileElephantsAreEating(): Int {
        val geodes = blueprints.take(3).map { blueprint ->
            maxGeodesFound = 0
            cache.clear()
            smashGeodes(blueprint, 32, Inventory.fromScratch())
        }
        return geodes.product()
    }

    /**
     * Simulates all relevant permutations of robot production,
     * ore collection and ultimately, geode smashing. Calculates
     * the [maxGeodesFound] for the given [blueprint].
     *
     * @param blueprint The robot specifications
     * @param minute The current time, in minutes, that has elapsed
     * @param inventory The current inventory of materials and robots
     *
     * @return The maximum number of geodes that can be smashed
     */
    private fun smashGeodes(blueprint: Blueprint, minute: Int, inventory: Inventory): Int {
        // If we're out of time, return the maximum we've found
        if (minute == 0) {
            maxGeodesFound = maxOf(maxGeodesFound, inventory.openGeodes)
            return inventory.openGeodes
        }

        // If even with the optimistic result we still don't smash more geodes
        // than the maximum already found, abandon this search
        if (inventory.optimisticGeodePrediction(minute) < maxGeodesFound) {
            return 0
        }

        // If we can afford to create a geode cracking robot, do it
        if (inventory.canAffordGeodeCrackingRobot(blueprint)) {
            val updatedInventory = inventory.createGeodeCrackingRobot(
                blueprint.geodeRobotCost.first,
                blueprint.geodeRobotCost.second
            )
            return searchDeeper(blueprint, minute, updatedInventory)
        }

        // If we have enough clay robots to always be able to make an obsidian robot,
        // and assuming we can afford one, make one
        if (inventory.producesEnoughClayToMakeObsidianRobots(blueprint) && inventory.canAffordClayRobot(blueprint)) {
            val updatedInventory = inventory.createObsidianCollectingRobot(
                blueprint.obsidianRobotCost.first,
                blueprint.obsidianRobotCost.second
            )
            return searchDeeper(blueprint, minute, updatedInventory)
        }

        // The next four decisions are impossible to determine the best
        // So we're tracking the best of them here
        var highest = 0

        // If we don't have enough obsidian robots to always be able to make a geode
        // smashing robot every minute, then lets explore the creation of another
        val hasEnoughObsidian = inventory.producesEnoughObsidianToMakeGeodeRobots(blueprint)
        if (!hasEnoughObsidian && inventory.canAffordObsidianRobot(blueprint)) {
            val updatedInventory = inventory.createObsidianCollectingRobot(
                blueprint.obsidianRobotCost.first,
                blueprint.obsidianRobotCost.second
            )
            highest = searchDeeper(blueprint, minute, updatedInventory, highest)
        }

        // If we don't have enough clay robots to always be able to make an obsidian
        // robot every minute, then lets explore the creation of another
        val hasEnoughClay = inventory.producesEnoughClayToMakeObsidianRobots(blueprint)
        if (!hasEnoughClay && inventory.canAffordClayRobot(blueprint)) {
            val updatedInventory = inventory.createClayCollectionRobot(blueprint.clayRobotCost)
            highest = searchDeeper(blueprint, minute, updatedInventory, highest)
        }

        // If we don't have enough ore robots to always be able to make a clay
        // robot every minute, then lets explore the creation of another
        val hasEnoughOre = inventory.producesEnoughOreToMakingEverything(blueprint)
        if (!hasEnoughOre && inventory.canAffordOreRobot(blueprint)) {
            val updatedInventory = inventory.createOreCollectionRobot(blueprint.oreRobotCost)
            highest = searchDeeper(blueprint, minute, updatedInventory, highest)
        }

        // If we don't have enough ore to make all of the robot types,
        // then lets explore what happens when we making nothing and just collect
        if (!hasEnoughOre) {
            val updatedInventory = inventory.createNothing()
            highest = searchDeeper(blueprint, minute, updatedInventory, highest)
        }

        return highest
    }

    /**
     * Recurse another level deeper in the geode smashing search.
     * Caches inventory states that have been seen before the reduce
     * the number of permutations that need to be simulated.
     *
     * @param blueprint The robot specifications
     * @param minute The current time, in minutes, that has elapsed
     * @param inventory The current inventory of materials and robots
     * @param compare The value with which to compare the result with
     *
     * @return The largest value between the result and [compare]
     */
    private fun searchDeeper(blueprint: Blueprint, minute: Int, inventory: Inventory, compare: Int = maxGeodesFound): Int {
        val geodes = cache.getOrPut(inventory.key()) {
            smashGeodes(blueprint, minute - 1, inventory)
        }
        return maxOf(compare, geodes)
    }
}