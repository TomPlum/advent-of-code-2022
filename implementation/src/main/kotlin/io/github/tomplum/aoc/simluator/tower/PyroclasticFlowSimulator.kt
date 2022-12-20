package io.github.tomplum.aoc.simluator.tower

import io.github.tomplum.libs.logging.AdventLogger
import io.github.tomplum.libs.math.point.Point2D
import kotlin.math.floor

class PyroclasticFlowSimulator(data: String) {

    private val flow = PyroclasticFlow(data)

    fun simulate(quantity: Long): Long {
        var currentRock = flow.getNextRock()
        var count = 0
        var rocks = 0L
        var x = 3 // Starts 2 units in (left wall == x=1)
        var y = 4 // Starts 3 units above the floor (floor == y=0)
        val snapshots = mutableListOf<Int>()

        val translationCache = mutableMapOf<Pair<RockType, Int>, List<Point2D>>()

        var hasJustCycled = false
        var lastCycleHeight = 0
        var rocksAtLastCycle = 0L
        val cycleMarkers = mutableListOf<Point2D>()
        val cycleHeightDeltas = mutableListOf<Int>()
        val rockDeltas = mutableListOf<Long>()
        var cycleFound = false
        var heightBeforeExtrapolation = 0
        var extrapolatedHeight = 0L
        val flowStates = mutableMapOf<FlowState, Pair<Int, Int>>()
        val heightsWhenCycling = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()

        while(rocks < quantity) {

            if (rocks > 1000) {
                val cacheKey = Pair(flow.rockIndex, flow.jetIndex)

                if (cacheKey in heightsWhenCycling) {
                    val (previousRocks, previousHeight) = heightsWhenCycling[cacheKey]!!
                    val period = rocks - previousRocks
                    if (period > 0 && rocks % period == quantity % period) {
                        val cycleHeight = flow.highestRockPosition - previousHeight
                        val remainingFullCycles = (quantity - rocks) / period
                        val extrapolatedRocks = remainingFullCycles * period
                        extrapolatedHeight = cycleHeight * remainingFullCycles
                        rocks += extrapolatedRocks
                    }
                } else {
                    heightsWhenCycling[cacheKey] = rocks.toInt() to flow.highestRockPosition
                }
            }

      /*      val lastTwoCycles = cycleHeightDeltas.takeLast(4)
            if (!cycleFound && lastTwoCycles.size == 4 && lastTwoCycles[0] == lastTwoCycles[2] && lastTwoCycles[1] == lastTwoCycles[3]) {
                heightBeforeExtrapolation = flow.highestRockPosition
                cycleFound = true
                val cycleHeight = cycleHeightDeltas.takeLast(2).sum()
                val rocksInCycle = rockDeltas.takeLast(2).sum()
                val remainingFullCycles = (quantity - rocks) / rocksInCycle
                val extrapolatedRocks = remainingFullCycles * rocksInCycle
                extrapolatedHeight = cycleHeight * remainingFullCycles
                rocks += extrapolatedRocks
            }*/

            val isInfluencedByJetStream = count % 2 == 0
            if (isInfluencedByJetStream) {
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
            } else {
                val yNew = y - 1
                val newRockPositions = currentRock.positions(Point2D(x, yNew)).reversed()
                //translationCache[Pair(currentRock.type, x)] = newRockPositions

                val rockWillHitRestingPoint = flow.hasAnyRocksResting(newRockPositions) || newRockPositions.any { pos -> pos.y == 0 }
                if (rockWillHitRestingPoint) {
                    flow.addRestingRock(currentRock.positions(Point2D(x, y)))
                    currentRock = flow.getNextRock()
                    x = 3
                    y = flow.highestRockPosition + currentRock.height() + 3
                    rocks += 1

                    //val flowState = FlowState(flow.ceiling(), rocks.toInt() % 5, flow.jetIndex % flow.jetPattern.size)
          /*          val flowState = FlowState(flow.rockIndex, flow.jetIndex)
                    if (flowState in flowStates) {
                        rockDeltas.add(rocks - rocksAtLastCycle)
                        rocksAtLastCycle = rocks

                        val cycleHeight = flow.highestRockPosition
                        val cycleHeightDelta = cycleHeight - lastCycleHeight
                        cycleHeightDeltas.add(cycleHeightDelta)

                        lastCycleHeight = cycleHeight
                        hasJustCycled = false
                    }
                    flowStates[flowState] = (rocks.toInt()) to flow.highestRockPosition*/


                    if (hasJustCycled) {
                        rockDeltas.add(rocks - rocksAtLastCycle)
                        rocksAtLastCycle = rocks

                        val cycleHeight = flow.highestRockPosition
                        val cycleHeightDelta = cycleHeight - lastCycleHeight
                        cycleHeightDeltas.add(cycleHeightDelta)

                        lastCycleHeight = cycleHeight
                        hasJustCycled = false
                    }
                } else {
                    y = yNew
                }
            }

            /*if (flow.jetIsCycling && flow.rockAreCycling) {
                hasJustCycled = true
            }*/

            count++
        }

        //cycleMarkers.forEach { pos -> flow.addCycleMarker(pos) }

        return (flow.highestRockPosition.toLong() - heightBeforeExtrapolation) + extrapolatedHeight + heightBeforeExtrapolation
    }

    //data class FlowState(val ceiling: List<Int>, val rockType: Int, val jetStream: Int)
    data class FlowState(val rockType: Int, val jetStream: Int)
}