package io.github.tomplum.aoc.communication.beacon

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.Direction.*
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

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
        val exclusionZones = sensors.flatMap { ( position, sensor ) ->
            val distance = position.distanceBetween(sensor.closestBeaconPosition!!)
            val topLeftQuadrant = position.getSensorRangeQuadrant(distance, LEFT, DOWN)
            val topRightQuadrant = position.getSensorRangeQuadrant(distance, RIGHT, DOWN)
            val bottomLeftQuadrant = position.getSensorRangeQuadrant(distance, LEFT, UP)
            val bottomRightQuadrant = position.getSensorRangeQuadrant(distance, RIGHT, UP)

            /*if (position == Point2D(8, 7)) {
                (topLeftQuadrant + topRightQuadrant + bottomLeftQuadrant + bottomRightQuadrant).forEach { pos ->
                    addTile(pos, TunnelTile('O'))
                }
                addTile(position, TunnelTile('S'))
            }*/
            topLeftQuadrant + topRightQuadrant + bottomLeftQuadrant + bottomRightQuadrant
        }.distinct()

        exclusionZones.forEach { pos ->
            val existing = getTile(pos, TunnelTile('.'))
            if (!existing.hasBeacon() && !existing.hasSensor()) {
                addTile(pos, TunnelTile('#'))
            }
        }

        excludedZones.addAll(exclusionZones)

        return exclusionZones.filter { it.y == y }.sortedBy { it.x }.count { getTile(it).isExclusionZone() }
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