package io.github.tomplum.aoc.map.pond

import io.github.tomplum.libs.math.map.AdventMap3D
import io.github.tomplum.libs.math.point.Point3D

class PondMap(scan: List<String>) : AdventMap3D<PondTile>() {
    init {
        scan.forEach { data ->
        val ( x, y, z ) = data.split(",").map { value -> value.toInt() }
            addTile(Point3D(x, y, z), PondTile())
        }
    }

    fun getLavaDropletSurfaceArea(): Int {
        return getDataMap().keys.sumOf { pos ->
            6 - pos.neighbouring().count { adj -> hasRecorded(adj) }
        }
    }

    private fun Point3D.neighbouring() = listOf(
        Point3D(this.x, this.y, this.z + 1), Point3D(this.x, this.y, this.z - 1)
    ) + this.planarAdjacentPoints()
}