package io.github.tomplum.aoc.map.volcano

import io.github.tomplum.libs.extensions.cartesianProduct
import java.util.*

class VolcanoCaveMap(scan: List<String>) {
    private val flowRates = mutableMapOf<Valve, Int>()

    private val valves: Map<Valve, List<Valve>> = scan.associate { line ->
        val label = line.removePrefix("Valve ").split(" ")[0].trim()
        val flowRate = line.split(" has flow rate=")[1].split(";")[0].toInt()
        val tunnels = if (line.contains("tunnels")) {
            line.split("tunnels lead to valves ")[1].trim().split(", ").map { Valve(it) }
        } else {
            listOf(line.split("tunnel leads to valve ")[1].trim()).map { Valve(it) }
        }
        flowRates[Valve(label)] = flowRate
        Valve(label) to tunnels
    }

    fun findMaximumFlowRate(): Int {
        /*val distances = mutableMapOf<Char, Int>()
        val next = PriorityQueue<Char>()

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
        }*/

        val valveLabels = valves.keys.toList()
        val distinctCombinations = valveLabels.cartesianProduct(valveLabels).filter { (a, b) -> a != b }
        val distances = Array(52) { Array(52) { -1 } }
        valveLabels.forEach { start ->
            valveLabels.forEach { end ->
                if (start != end) {
                    distances[start.index][end.index] = findShortestPath(start, end).size - 1
                }
            }
        }

        val valveCandidates = valveLabels.filter { label -> flowRates[label]!! > 0 }

        val rates = calculateFlowRates(distances, Valve("AA"), 30, valveCandidates)
        return rates.map { rates ->
            rates.entries.fold(0) { pressure, (valve, time) -> pressure + flowRates[valve]!! * time }
        }.max()

        /*rates.map { rates ->
            rates.entries.sumOf { (valve, time) -> flowRates[valve]!! * time }
        }.maxOf { pressure -> pressure }*/
    }

    private fun calculateFlowRates(
        paths: Array<Array<Int>>,
        source: Valve,
        time: Int,
        remaining: List<Valve>,
        opened: Map<Valve, Int> = mutableMapOf()
    ): List<Map<Valve, Int>> {
        // A list of valve label -> time that valve spends open
        val flowRates = mutableListOf(opened)

        remaining.forEachIndexed { i, target ->
            val distance = paths[source.index][target.index]
            val newTime = time - distance - 1
            if (newTime < 1) {
                return@forEachIndexed
            }

            val newlyOpened = opened.toMutableMap()
            newlyOpened[target] = newTime

            val newRemaining = remaining.toMutableList()
            newRemaining.removeAt(i)

            flowRates.addAll(calculateFlowRates(paths, target, newTime, newRemaining, newlyOpened))
        }

        return flowRates
    }

    private fun findShortestPath(startingValve: Valve, finishingValve: Valve): List<Valve> {
        val visited = mutableSetOf(startingValve)

        val next = PriorityQueue<Valve>()
        next.offer(startingValve)

        while(next.isNotEmpty()) {
            val path = listOf(next.poll())
            val valve = path.last()

            valves[valve]?.forEach { adjacent ->
                if (adjacent in visited) {
                    return@forEach
                }

                val updatedPath = path + adjacent
                if (adjacent == finishingValve) {
                    return updatedPath
                }

                visited.add(adjacent)
                next.addAll(updatedPath)
            }
        }

        throw IllegalArgumentException("Could not find path from $startingValve -> $finishingValve")
    }
}