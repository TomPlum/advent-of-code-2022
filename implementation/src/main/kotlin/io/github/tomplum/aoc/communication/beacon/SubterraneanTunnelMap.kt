package io.github.tomplum.aoc.communication.beacon

import io.github.tomplum.libs.math.point.Point2D
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class SubterraneanTunnelMap(data: List<String>) {

    /**
     * A map of sensor locations -> their closest beacon location.
     */
    val sensors = mutableMapOf<Point2D, Point2D>()

    /**
     * A map of sensor locations -> the distance to their closest beacon
     */
    private val distances = mutableMapOf<Point2D, Int>()

    private val excludedZones = mutableSetOf<Point2D>()

    init {
        data.forEach { input ->
            val sensorData = input.removePrefix("Sensor at ").split(": ")[0].split(", ")
            val sensorPos = Point2D(sensorData[0].removePrefix("x=").toInt(), sensorData[1].removePrefix("y=").toInt())

            val beaconData = input.split(" closest beacon is at ")[1].trim().split(", ")
            val beaconPos = Point2D(beaconData[0].removePrefix("x=").toInt(), beaconData[1].removePrefix("y=").toInt())

            sensors[sensorPos] = beaconPos
            distances[sensorPos] = sensorPos.distanceBetween(beaconPos)
            excludedZones.add(sensorPos)
        }
    }

    fun locateExcludedPositions(y: Int): MutableSet<IntRange> {
        val xExclusionZoneRange = mutableSetOf<IntRange>()
        sensors.keys.forEach { sensor ->
            val distance = distances[sensor]!!
            val dy = y - sensor.y

            if (abs(dy) > distance) {
                return@forEach
            }

            val dx = distance - abs(dy)
            val xRangeWhereNoBeacons = (-dx + sensor.x)..(dx + sensor.x)
            xExclusionZoneRange.add(xRangeWhereNoBeacons)
        }

        return xExclusionZoneRange
    }

    fun locateDistressBeacon(yOrdinateMaximum: Int): LongPoint2D {
        (0..yOrdinateMaximum).forEach ordinates@{ y ->
            val noBeacons = locateExcludedPositions(y)
            val ranges = noBeacons.sortedBy { range -> range.first }
            var biggestRange = ranges.first()
            if (biggestRange.first > 0) {
                return@ordinates
            }

            ranges.forEach ranges@{ range ->
                if (biggestRange.contains(range)) {
                    return@ranges
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

    private fun IntRange.contains(other: IntRange): Boolean {
        return this.first <= other.first && other.last <= this.last
    }
}