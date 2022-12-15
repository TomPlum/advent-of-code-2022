package io.github.tomplum.aoc.communication.beacon

import io.github.tomplum.libs.math.map.MapTile
import io.github.tomplum.libs.math.point.Point2D

class TunnelTile(private val id: Char): MapTile<Char>(id) {

    var closestBeaconPosition: Point2D? = null


}