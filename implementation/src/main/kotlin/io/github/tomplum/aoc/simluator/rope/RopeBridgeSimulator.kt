package io.github.tomplum.aoc.simluator.rope

import io.github.tomplum.libs.logging.AdventLogger
import io.github.tomplum.libs.math.Direction

class RopeBridgeSimulator(private val directions: List<String>) {

    private val bridge = RopeBridge()

    fun countUniquePositionsVisited(): Int {
        AdventLogger.debug("== Initial State ==")
        AdventLogger.debug(bridge)

        directions.map { value ->
            val parts = value.split(" ")
            val direction = when(parts[0]) {
                "U" -> Direction.UP
                "R" -> Direction.RIGHT
                "D" -> Direction.DOWN
                "L" -> Direction.LEFT
                else -> throw IllegalArgumentException("Unknown Direction [${parts[0]}]")
            }
            val units = parts[1].toInt()
            Pair(direction, units)
        }.forEach { (direction, units) ->
            AdventLogger.debug("== ${direction.name} $units ==")
            repeat(units) {
                bridge.move(direction)
                AdventLogger.debug(bridge)
            }
        }

        return bridge.visited.size
    }
}