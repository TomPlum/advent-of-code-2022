package io.github.tomplum.aoc.communication.beacon

class EmergencySensorSystem(data: List<String>) {

    private val subterraneanTunnelMap = SubterraneanTunnelMap(data)

    fun calculateBeaconExclusionZoneCount(yAxisOrdinate: Int): Int {
        val exclusions = subterraneanTunnelMap.locateExcludedPositions(yAxisOrdinate)
            .flatten()
            .distinct()

        val beacons = subterraneanTunnelMap.sensors
            .values
            .filter { point -> point.y == yAxisOrdinate }
            .distinct()
        
        return exclusions.size - beacons.size
    }

    fun locateDistressBeacon(): Long {
        val yOrdinateMax = 4_000_000
        val position = subterraneanTunnelMap.locateDistressBeacon(yOrdinateMax)
        return (yOrdinateMax * position.x) + position.y // Return tuning frequency
    }
}