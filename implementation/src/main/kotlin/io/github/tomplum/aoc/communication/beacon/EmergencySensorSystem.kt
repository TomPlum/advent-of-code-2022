package io.github.tomplum.aoc.communication.beacon

class EmergencySensorSystem(data: List<String>) {

    private val subterraneanTunnelMap = SubterraneanTunnelMap(data)

    fun calculateBeaconExclusionZoneCount(yAxisOrdinate: Int): Int {
        return subterraneanTunnelMap.doThing(yAxisOrdinate)
    }
}