package io.github.tomplum.aoc.simluator.rope

import io.github.tomplum.libs.math.Direction

/**
 * Due to nebulous reasoning involving Planck lengths,
 * the [RopeBridgeSimulator] models the positions of knots on
 * a two-dimensional grid. Then, by following a hypothetical
 * series of [directions] for the head, it determines how the
 * tail will move.
 *
 * @param directions A list of direction that the head should follow
 */
class RopeBridgeSimulator(private val directions: List<String>) {
    /**
     * Calculates the unique positions visited by the tail of
     * the rope from the bridge after simulating its behaviour.
     *
     * @param knotQuantity The number of knots to simulate
     * @return The number of unique positions visited
     */
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