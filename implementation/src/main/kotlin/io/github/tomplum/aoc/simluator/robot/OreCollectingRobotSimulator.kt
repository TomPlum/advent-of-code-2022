package io.github.tomplum.aoc.simluator.robot

import io.github.tomplum.libs.extensions.product

class OreCollectingRobotSimulator(data: List<String>) {

    private val blueprints = data.map { info -> Blueprint.fromString(info) }

    private val cache = mutableMapOf<String, Int>()
    private var maxGeodesFound = 0

    fun simulate(): Int {
        val geodes = blueprints.asSequence().map { blueprint ->
            maxGeodesFound = 0
            cache.clear()
            blueprint.id to calculateGeodesSmashed(blueprint, 24, Inventory.fromScratch())
        }
        return geodes.sumOf { (id, geodes) -> id * geodes }
    }

    fun simulate2(): Int {
        val geodes = blueprints.take(3).map { blueprint ->
            maxGeodesFound = 0
            cache.clear()
            calculateGeodesSmashed(blueprint, 32, Inventory.fromScratch())
        }
        return geodes.product()
    }

    private fun calculateGeodesSmashed(blueprint: Blueprint, minute: Int, inventory: Inventory): Int {
        if (minute == 0) {
            maxGeodesFound = maxOf(maxGeodesFound, inventory.openGeodes)
            return inventory.openGeodes
        }

        if (inventory.optimisticGeodePrediction(minute) < maxGeodesFound) {
            return 0
        }

        if (inventory.canAffordGeodeCrackingRobot(blueprint)) {
            val updatedInventory = inventory.createGeodeCrackingRobot(
                blueprint.geodeRobotCost.first,
                blueprint.geodeRobotCost.second
            )
            return searchDeeper(blueprint, minute, updatedInventory)
        }

        if (inventory.producesEnoughClayToMakeObsidianRobots(blueprint) && inventory.canAffordClayRobot(blueprint)) {
            val updatedInventory = inventory.createObsidianCollectingRobot(
                blueprint.obsidianRobotCost.first,
                blueprint.obsidianRobotCost.second
            )
            return searchDeeper(blueprint, minute, updatedInventory)
        }

        var highest = 0

        val hasEnoughObsidian = inventory.producesEnoughObsidianToMakeGeodeRobots(blueprint)
        if (!hasEnoughObsidian && inventory.canAffordObsidianRobot(blueprint)) {
            val updatedInventory = inventory.createObsidianCollectingRobot(
                blueprint.obsidianRobotCost.first,
                blueprint.obsidianRobotCost.second
            )
            highest = searchDeeper(blueprint, minute, updatedInventory, highest)
        }

        val hasEnoughClay = inventory.producesEnoughClayToMakeObsidianRobots(blueprint)
        if (!hasEnoughClay && inventory.canAffordClayRobot(blueprint)) {
            val updatedInventory = inventory.createClayCollectionRobot(blueprint.clayRobotCost)
            highest = searchDeeper(blueprint, minute, updatedInventory, highest)
        }

        val hasEnoughOre = inventory.producesEnoughOreToMakingEverything(blueprint)
        if (!hasEnoughOre && inventory.canAffordOreRobot(blueprint)) {
            val updatedInventory = inventory.createOreCollectionRobot(blueprint.oreRobotCost)
            highest = searchDeeper(blueprint, minute, updatedInventory, highest)
        }

        if (!hasEnoughOre) {
            val updatedInventory = inventory.createNothing()
            highest = searchDeeper(blueprint, minute, updatedInventory, highest)
        }

        return highest
    }

    private fun searchDeeper(blueprint: Blueprint, minute: Int, inventory: Inventory, compare: Int = maxGeodesFound): Int {
        val geodes = cache.getOrPut(inventory.key()) {
            calculateGeodesSmashed(blueprint, minute - 1, inventory)
        }
        return maxOf(compare, geodes)
    }
}