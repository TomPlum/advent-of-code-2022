package io.github.tomplum.aoc.simluator.rope

import io.github.tomplum.libs.math.Direction

class RopeBridgeSimulator(private val directions: List<String>) {

    fun countUniquePositionsVisited(knotQuantity: Int = 1): Int {
        val bridge = RopeBridge(knotQuantity)

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
            repeat(units) {
                bridge.move(direction)
            }
        }

        return bridge.visited.size
    }
}