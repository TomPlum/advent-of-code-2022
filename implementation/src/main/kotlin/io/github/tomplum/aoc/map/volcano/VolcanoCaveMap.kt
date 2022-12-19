package io.github.tomplum.aoc.map.volcano

class VolcanoCaveMap(scan: List<String>) {

    private val valveRelationships: Map<Valve, List<Valve>> = scan.associate { line ->
        val label = line.removePrefix("Valve ").split(" ")[0].trim()
        val flowRate = line.split(" has flow rate=")[1].split(";")[0].toInt()
        val tunnels = if (line.contains("tunnels")) {
            line.split("tunnels lead to valves ")[1].trim().split(", ").map { label -> Valve(label) }
        } else {
            listOf(line.split("tunnel leads to valve ")[1].trim()).map { label -> Valve(label) }
        }
        val valve = Valve(label)
        valve.flowRate = flowRate
        valve to tunnels
    }

    fun findMaximumReleasablePressure(): Int {
        val valves = valveRelationships.keys.toList()

        val distances = valves.fold(mutableMapOf<Valve, MutableMap<Valve, Int>>()) { sources, start ->
            valves.forEach { end ->
                val targets = sources.getOrPut(start) { mutableMapOf() }
                targets[end] = findShortestPath(start, end).size - 1
                sources[start] = targets
            }
            sources
        }

        val flowingValves = valves.filter { valve -> valve.flowRate > 0 }
        val times = calculateValveOpenTime(distances, Valve("AA"), 30, flowingValves)
        val pressuresReleased = times.map { rates ->
            rates.entries.fold(0) { pressure, (valve, time) -> pressure + valve.flowRate * time }
        }

        return pressuresReleased.max()
    }

    fun findMaximumReleasablePressureWithElephant(): Int {
        val valves = valveRelationships.keys.toList()

        val distances = valves.fold(mutableMapOf<Valve, MutableMap<Valve, Int>>()) { sources, start ->
            valves.forEach { end ->
                val targets = sources.getOrPut(start) { mutableMapOf() }
                targets[end] = findShortestPath(start, end).size - 1
                sources[start] = targets
            }
            sources
        }

        val flowingValves = valves.filter { valve -> valve.flowRate > 0 }
        val times = calculateValveOpenTime(distances, Valve("AA"), 26, flowingValves)

        return 0
    }

    private fun calculateValveOpenTime(
        distances: Map<Valve, Map<Valve, Int>>,
        source: Valve,
        time: Int,
        remaining: List<Valve>,
        opened: Map<Valve, Int> = mutableMapOf()
    ): List<Map<Valve, Int>> {
        val cumulativeTime = mutableListOf(opened)

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

            cumulativeTime.addAll(calculateValveOpenTime(distances, target, newTime, newRemaining, newlyOpened))
        }

        return cumulativeTime
    }

    private fun findShortestPath(start: Valve, end: Valve): List<Valve> {
        if (start == end) {
            return listOf(start)
        }

        val visited = mutableSetOf(start)

        val next = mutableListOf<List<Valve>>()
        next.add(listOf(start))

        while(next.isNotEmpty()) {
            val path = next.removeFirst()
            val valve = path.last()

            valveRelationships[valve]?.forEach { adjacent ->
                if (adjacent in visited) {
                    return@forEach
                }

                val updatedPath = path + adjacent
                if (adjacent == end) {
                    return updatedPath
                }

                visited.add(adjacent)
                next.add(updatedPath)
            }
        }

        throw IllegalArgumentException("Could not find path from $start -> $end")
    }
}