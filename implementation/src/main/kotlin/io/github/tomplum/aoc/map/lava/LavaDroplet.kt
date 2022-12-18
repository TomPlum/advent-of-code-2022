package io.github.tomplum.aoc.map.lava

import io.github.tomplum.libs.math.map.AdventMap3D
import io.github.tomplum.libs.math.point.Point3D

class LavaDroplet(scan: List<String>) : AdventMap3D<PondTile>() {
    init {
        scan.forEach { data ->
            val (x, y, z) = data.split(",").map { value -> value.toInt() }
            addTile(Point3D(x, y, z), PondTile())
        }
    }

    /**
     * Calculates the surface area of the lava droplet,
     * including air pockets on the inside.
     * @return The total surface area.
     */
    fun getSurfaceArea(): Int = getDataMap().keys.sumOf { pos ->
        6 - pos.neighbouring().count { adj -> hasRecorded(adj) }
    }

    /**
     * Calculates the external surface area of the lava droplet.
     * Only considers faces on the outside that
     * can be touched by pond water.
     * @return The total exterior surface area
     */
    fun getExteriorSurfaceArea(): Int {
        val surface = locateSurfaceCubes()
        return getDataMap().keys.sumOf { pos -> pos.neighbouring().filter { adj -> adj in surface }.size }
    }

    private fun locateSurfaceCubes(): MutableSet<Point3D> {
        val xMin = xMin() - 1
        val yMin = yMin()!! - 1
        val zMin = zMin()!! - 1

        val xInside = xMin..xMax() + 1
        val yInside = yMin..yMax()!! + 1
        val zInside = zMin..zMax()!! + 1

        val surface = mutableSetOf<Point3D>()
        val queue = mutableListOf<Point3D>()
        queue.add(Point3D(xMin, yMin, zMin))

        while (queue.isNotEmpty()) {
            val position = queue.removeLast()

            if (hasRecorded(position)) {
                continue
            }

            if (position.x !in xInside || position.y !in yInside || position.z !in zInside) {
                continue
            }

            if (surface.add(position)) {
                queue.addAll(position.neighbouring())
            }
        }

        return surface
    }

    override fun xMin(): Int {
        return getDataMap().keys.minBy { pos -> pos.x }.x
    }

    override fun xMax(): Int {
        return getDataMap().keys.maxBy { pos -> pos.x }.x
    }

    /**
     * Finds the six neighbouring points.
     * @return The 6 neighbouring co-ordinates.
     */
    private fun Point3D.neighbouring() = listOf(
        Point3D(this.x, this.y, this.z + 1), Point3D(this.x, this.y, this.z - 1)
    ) + this.planarAdjacentPoints()
}