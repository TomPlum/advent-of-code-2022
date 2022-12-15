package io.github.tomplum.aoc.communication.beacon

import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class SubterraneanTunnelMap(data: List<String>) : AdventMap2D<TunnelTile>() {

    val sensors = mutableMapOf<Point2D, TunnelTile>()
    private val excludedZones = mutableSetOf<Point2D>()

    init {
        data.forEach { input ->
            val sensorData = input.removePrefix("Sensor at ").split(": ")[0].split(", ")
            val sensorPos = Point2D(sensorData[0].removePrefix("x=").toInt(), sensorData[1].removePrefix("y=").toInt())

            val beaconData = input.split(" closest beacon is at ")[1].trim().split(", ")
            val beaconPos = Point2D(beaconData[0].removePrefix("x=").toInt(), beaconData[1].removePrefix("y=").toInt())

            val sensor = TunnelTile('S')
            sensor.closestBeaconPosition = beaconPos
            addTile(sensorPos, sensor)
            sensors[sensorPos] = sensor
            excludedZones.add(sensorPos)

            addTile(beaconPos, TunnelTile('B'))
        }
    }

    fun locatePositionsWhereNoBeaconsCanBePresent(y: Int): MutableSet<IntRange> {
        val xExclusionZoneRange = mutableSetOf<IntRange>()
        sensors.forEach { ( position, sensor ) ->
            val distance = position.distanceBetween(sensor.closestBeaconPosition!!)
            val dy = y - position.y
            if (abs(dy) > distance) {
                return@forEach
            }

            val dx = distance - abs(dy)
            xExclusionZoneRange.add(-dx + position.x..dx + position.x)
        }

        return xExclusionZoneRange
    }

    fun locateDistressBeacon(yOrdinateMaximum: Int): LongPoint2D {
        (0..yOrdinateMaximum).forEach { y->
            val noBeacons = locatePositionsWhereNoBeaconsCanBePresent(y)
            val ranges = noBeacons.sortedBy { range -> range.first }
            var biggestRange = ranges.first()
            if (biggestRange.first > 0) {
                return@forEach
            }

            ranges.forEach { range ->
                if (biggestRange.contains(range)) {
                    return@forEach
                }

                if (biggestRange.overlaps(range) || biggestRange.last + 1 == range.first) {
                    biggestRange = min(biggestRange.first, range.first)..max(biggestRange.last, range.last)
                } else {
                    return LongPoint2D(biggestRange.last + 1L, y.toLong())
                }
            }
        }

        throw IllegalArgumentException("Failed to locate distress beacon in range 0 >= loc <= $yOrdinateMaximum")
    }

    inner class LongPoint2D(val x: Long, val y: Long)

    private fun IntRange.overlaps(other: IntRange): Boolean {
        return this.contains(other.first) || this.contains(other.last) || other.contains(this.first)
    }

    private  fun IntRange.contains(other: IntRange): Boolean {
        return this.first <= other.first && other.last <= this.last
    }
}