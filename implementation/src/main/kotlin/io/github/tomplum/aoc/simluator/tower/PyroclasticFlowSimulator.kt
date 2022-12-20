package io.github.tomplum.aoc.simluator.tower

import io.github.tomplum.libs.math.point.Point2D

/**
 * Your handheld device has located an alternative exit from the cave for you and the elephants.
 * The ground is rumbling almost continuously now, but the strange valves bought you some time.
 * It's definitely getting warmer in here, though.
 *
 * The tunnels eventually open into a very tall, narrow chamber. Large, oddly-shaped rocks are
 * falling into the chamber from above, presumably due to all the rumbling. If you can't work
 * out where the rocks will fall next, you might be crushed!
 *
 * Simulates the flow of rocks over time given the jet-stream data.
 *
 * @param data A list of directions indicating the jet-streams influence
 */
class PyroclasticFlowSimulator(data: String) {

    private val flow = PyroclasticFlow(data)

    private var count = 0
    private var rocks = 0L

    private var x = 3
    private var y = 4
    private var extrapolatedHeight = 0L

    private var currentRock = flow.getNextRock()

    /**
     * Simulates the flow for the given [quantity] of rocks.
     * A rock is considered simulated when it has come to rest
     * on either the floor or another rock at rest.
     *
     * @param quantity The number of rocks to simulate falling
     * @return The height of the tower after all rocks have come to rest
     */
    fun simulate(quantity: Long): Long {
        val states = mutableMapOf<StateKey, StateValue>()

        while(rocks < quantity) {

            if (rocks > 1000) {
                val key = StateKey(flow.rockIndex, flow.jetIndex)

                if (key in states) {
                    val (extrapolatedRocks, extrapolatedHeight) = states[key]!!.extrapolateFlowHeight(quantity)
                    rocks += extrapolatedRocks
                    this.extrapolatedHeight = extrapolatedHeight
                } else {
                    states[key] = StateValue(rocks.toInt(), flow.highestRockPosition)
                }
            }

            val isInfluencedByJetStream = count % 2 == 0

            if (isInfluencedByJetStream) {
                pushRockViaJetStream()
            } else {
                letRockFall()
            }

            count++
        }

        return flow.highestRockPosition.toLong() + extrapolatedHeight
    }

    /**
     * Shifts the [currentRock] either left or right in
     * the [x] axis by one unit relative to the current
     * jet stream direction.
     *
     * If the rock cannot be shifted due to a potential
     * collision with another rock at rest, or where it
     * would exit the bounds of the flow range, then
     * it is not moved.
     */
    private fun pushRockViaJetStream() {
        val direction = flow.getNextJetPatternDirection()
        val xLeftNew = Point2D(x, y).shift(direction).x
        val newRockPositions = currentRock.positions(Point2D(xLeftNew, y))

        val xBoundary = (1..7)
        val xRightNew = currentRock.xRightMost(xLeftNew)

        val isWithinBounds = xLeftNew in xBoundary && xRightNew in xBoundary
        val isShiftingIntoRock = flow.hasAnyRocksResting(newRockPositions)

        if (isWithinBounds && !isShiftingIntoRock) {
            x = xLeftNew
        }
    }

    /**
     * Shifts the [currentRock] one unit down in the [y] axis.
     *
     * If the rock is about to collide with a rock that is
     * already at rest, then the [currentRock] is considered
     * to also be at rest and the next rock starts falling.
     */
    private fun letRockFall(): Rock {
        val yNew = y - 1
        val newRockPositions = currentRock.positions(Point2D(x, yNew)).reversed()

        val isComingToRest = flow.hasAnyRocksResting(newRockPositions) || newRockPositions.any { pos -> pos.y == 0 }
        if (isComingToRest) {
            flow.addRestingRock(currentRock.positions(Point2D(x, y)))
            currentRock = flow.getNextRock()
            x = 3
            y = flow.highestRockPosition + currentRock.height() + 3
            rocks += 1
        } else {
            y = yNew
        }
        return currentRock
    }

    /**
     * Represents a unique identifier of the cyclomatic
     * nature of both the types of falling rocks and the
     * jet stream.
     *
     * @param rockTypeIndex The index of the current falling rock (0 - 4)
     * @param jetStreamIndex The index of the current jet stream direction (0 - stream.length-1)
     */
    data class StateKey(val rockTypeIndex: Int, val jetStreamIndex: Int)

    /**
     * Represents the relevant values that are used to extrapolate
     * the future height of the tower once a cycle has been found.
     *
     * @param previousFlowHeight The number of rocks that had come to rest at the time
     * @param previousRockCount The height of the tower at the time
     */
    inner class StateValue(private val previousRockCount: Int, private val previousFlowHeight: Int) {
        /**
         * Calculates the size of the rock cycle (period) and uses
         * it to calculate the height of the tower when the
         * [rockQuantity] is reached.
         *
         * @param rockQuantity The total number of rocks that are to be dropped
         * @return The number of rocks and the height of them after all cycles
         */
        fun extrapolateFlowHeight(rockQuantity: Long): Pair<Long, Long> {
            val period = rocks - previousRockCount
            if (period > 0 && rocks % period == rockQuantity % period) {
                val cycleHeight = flow.highestRockPosition - previousFlowHeight
                val remainingFullCycles = (rockQuantity - rocks) / period
                val extrapolatedRocks = remainingFullCycles * period
                val extrapolatedHeight = cycleHeight * remainingFullCycles
                return extrapolatedRocks to extrapolatedHeight
            }

            return 0L to 0L
        }
    }
}