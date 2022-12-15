package io.github.tomplum.aoc.communication.beacon

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.Direction.*
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D
import kotlin.math.abs

class SubterraneanTunnelMap(data: List<String>) : AdventMap2D<TunnelTile>() {

    private val sensors = mutableMapOf<Point2D, TunnelTile>()
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

    fun doThing(y: Int): Int {
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

        val xOrdinatesExcluded = xExclusionZoneRange.flatten().distinct()
        val beacons = sensors.map { sensor -> sensor.value.closestBeaconPosition!! }.filter { point -> point.y == y }.distinct()
        return xOrdinatesExcluded.size - beacons.size
    }

    private fun Point2D.getSensorRangeQuadrant(distance: Int, horizontal: Direction, vertical: Direction): List<Point2D> {
        return (0..distance).flatMapIndexed { i, x ->
            val start = this.shift(horizontal, i)
            val column = (1..(distance - i)).map { upOffset ->
                start.shift(vertical, upOffset)
            }

            column + start
        }
    }
}