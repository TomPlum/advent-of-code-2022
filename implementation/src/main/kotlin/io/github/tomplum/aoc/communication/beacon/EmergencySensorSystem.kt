package io.github.tomplum.aoc.communication.beacon

class EmergencySensorSystem(data: List<String>) {

    private val subterraneanTunnelMap = SubterraneanTunnelMap(data)

    fun calculateBeaconExclusionZoneCount(yAxisOrdinate: Int): Int {
        val xOrdinates = subterraneanTunnelMap.locatePositionsWhereNoBeaconsCanBePresent(yAxisOrdinate)
            .flatten().distinct()

        val beacons = subterraneanTunnelMap.sensors
            .map { sensor -> sensor.value.closestBeaconPosition!! }
            .filter { point -> point.y == yAxisOrdinate }
            .distinct()
        return xOrdinates.size - beacons.size
    }

    fun locateDistressBeacon(): Long {
        val yOrdinateMaximum = 4_000_000
        val position = subterraneanTunnelMap.locateDistressBeacon(yOrdinateMaximum)
        return (yOrdinateMaximum * position.x) + position.y // Return tuning frequency
    }
}