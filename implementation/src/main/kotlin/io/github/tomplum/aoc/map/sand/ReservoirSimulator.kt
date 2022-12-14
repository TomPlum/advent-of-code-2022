package io.github.tomplum.aoc.map.sand

import io.github.tomplum.libs.math.Direction

class ReservoirSimulator(private val reservoir: RegolithReservoir) {

    fun simulate(): Int {
        var sandPosition = reservoir.entryPoint

        var units = 0

        var sandFlowingIntoAbyss = false

        while(!sandFlowingIntoAbyss) {
            var atRest = false
            var tilesTraversed = 0

            while(!atRest && !sandFlowingIntoAbyss) {
                if (tilesTraversed > 180) {
                    sandFlowingIntoAbyss = true
                }

                val positionBelow = sandPosition.shift(Direction.UP)
                val tileBelow = reservoir.getTile(positionBelow)
                val (isNowResting, newSandPosition, newTilesTraversed) = reservoir.checkForBlockingTiles(
                    tileBelow,
                    sandPosition,
                    tilesTraversed,
                    positionBelow
                )
                atRest = isNowResting
                sandPosition = newSandPosition
                tilesTraversed = newTilesTraversed
            }

            if (!sandFlowingIntoAbyss) {
                reservoir.addSand(sandPosition)
                sandPosition = reservoir.entryPoint
                units += 1
            }
        }

        return units
    }

    fun findSafeFloorSpace(): Int {
        var sandPosition = reservoir.entryPoint
        val yFloor = reservoir.getFloorOrdinateY()

        var units = 0

        var sandBlockingSource = false

        while(!sandBlockingSource) {
            var atRest = false

            while(!atRest && !sandBlockingSource) {
                val positionBelow = sandPosition.shift(Direction.UP)
                val tileBelow = reservoir.getTile(positionBelow)

                if (reservoir.isEntryPointBlocked()) {
                    sandBlockingSource = true
                }

                if (positionBelow.y == yFloor) {
                    atRest = true
                } else {
                    val (isNowResting, newSandPosition) = reservoir.checkForBlockingTiles(
                        tileBelow,
                        sandPosition,
                        0,
                        positionBelow
                    )
                    atRest = isNowResting
                    sandPosition = newSandPosition
                }
            }

            if (!sandBlockingSource) {
                reservoir.addSand(sandPosition)
                sandPosition = reservoir.entryPoint
                units += 1
            }
        }

        return units
    }
}