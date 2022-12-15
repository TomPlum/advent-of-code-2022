package io.github.tomplum.aoc.solutions

import io.github.tomplum.aoc.communication.beacon.EmergencySensorSystem
import io.github.tomplum.libs.input.Day
import io.github.tomplum.libs.input.InputReader
import io.github.tomplum.libs.solutions.Solution

class Day15 : Solution<Int, Long> {
    private val data = InputReader.read<String>(Day(15)).value
    private val emergencySensorSystem = EmergencySensorSystem(data)

    override fun part1(): Int {
        return emergencySensorSystem.calculateBeaconExclusionZoneCount(2000000)
    }

    override fun part2(): Long {
        return emergencySensorSystem.locateDistressBeacon()
    }
}