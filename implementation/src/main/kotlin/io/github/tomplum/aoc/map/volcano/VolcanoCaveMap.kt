package io.github.tomplum.aoc.map.volcano

import java.util.*

class VolcanoCaveMap(scan: List<String>) {
    private val flowRates = mutableMapOf<String, Int>()

    private val valves: Map<String, List<String>> = scan.associate { line ->
        val label = line.removePrefix("Valve ").split(" ")[0].trim()
        val flowRate = line.split(" has flow rate=")[1].split(";")[0].toInt()
        val tunnels = if (line.contains("tunnels")) {
            line.split("tunnels lead to valves ")[1].trim().split(", ")
        } else {
            listOf(line.split("tunnel leads to valve ")[1].trim())
        }
        flowRates[label] = flowRate
        label to tunnels
    }

    fun findMaximumFlowRate() {
        val distances = mutableMapOf<String, Int>()
        val next = PriorityQueue<String>()

        val start = valves.keys.first()
        next.offer(start)
        distances[start] = 0

        while(next.isNotEmpty()) {
            val currentValve = next.poll()!!
            val distance = distances[currentValve]!!
            valves[currentValve]?.forEach { adjacentValve ->
                val updatedDistance = distance + 1
                if (updatedDistance < distances.getOrDefault(adjacentValve, Int.MAX_VALUE)) {
                    distances[adjacentValve] = updatedDistance
                    next.add(adjacentValve)
                }
            }
        }

        val valveCandidates = valves.filterKeys { label -> flowRates[label]!! > 0 }

        val s = ""
    }

    private fun find() {

    }

}