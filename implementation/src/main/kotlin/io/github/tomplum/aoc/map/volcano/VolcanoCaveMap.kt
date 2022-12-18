package io.github.tomplum.aoc.map.volcano

class VolcanoCaveMap(scan: List<String>) {
    private val flowRates = mutableMapOf<Valve, Int>()

    private val valveRelationships: Map<Valve, List<Valve>> = scan.associate { line ->
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

    var times = listOf<Map<Valve, Int>>()
    val distances: Array<Array<Int>> = Array(52) { Array(52) { -1 } }

    fun findMaximumFlowRate(): Int {
        val valves = valveRelationships.keys.toList()
        val newDistances = mutableMapOf<Valve, MutableMap<Valve, Int>>()
        valves.forEach { start ->
            valves.forEach { end ->
                val innerMap = newDistances.getOrPut(start) { mutableMapOf() }
                innerMap[end] = findShortestPath(start, end).size - 1
            }
        }

        val valveCandidates = valves.filter { label -> flowRates[label]!! > 0 }

        val times = calculateFlowRates(newDistances, Valve("AA"), 30, valveCandidates)
        this.times = times
        val flowRates = times.map { rates ->
            rates.entries.fold(0) { rate, (valve, time) -> rate + flowRates[valve]!! * time }
        }

        return flowRates.max()

        /*rates.map { rates ->
            rates.entries.sumOf { (valve, time) -> flowRates[valve]!! * time }
        }.maxOf { pressure -> pressure }*/
    }

    private fun calculateFlowRates(
        distances: Map<Valve, Map<Valve, Int>>,
        source: Valve,
        time: Int,
        remaining: List<Valve>,
        opened: Map<Valve, Int> = mutableMapOf()
    ): List<Map<Valve, Int>> {
        // A list of valve label -> time that valve spends open
        val flowRates = mutableListOf(opened)

        remaining.forEachIndexed { i, target ->
            val distance = distances[source]?.get(target)!!
            val newTime = time - distance - 1
            if (newTime < 1) {
                return@forEachIndexed
            }

            val newlyOpened = opened.toMutableMap()
            newlyOpened[target] = newTime

            val newRemaining = remaining.toMutableList()
            newRemaining.removeAt(i)

            flowRates.addAll(calculateFlowRates(distances, target, newTime, newRemaining, newlyOpened))
        }

        return flowRates
    }

    private fun findShortestPath(startingValve: Valve, finishingValve: Valve): List<Valve> {
        if (startingValve == finishingValve) {
            return listOf(startingValve)
        }

        val visited = mutableSetOf(startingValve)

        val next = mutableListOf<List<Valve>>()
        next.add(listOf(startingValve))

        while(next.isNotEmpty()) {
            val path = next.removeFirst()
            val valve = path.last()

            valveRelationships[valve]?.forEach { adjacent ->
                if (adjacent in visited) {
                    return@forEach
                }

                val updatedPath = path + adjacent
                if (adjacent == finishingValve) {
                    return updatedPath
                }

                visited.add(adjacent)
                next.add(updatedPath)
            }
        }

        throw IllegalArgumentException("Could not find path from $startingValve -> $finishingValve")
    }
}