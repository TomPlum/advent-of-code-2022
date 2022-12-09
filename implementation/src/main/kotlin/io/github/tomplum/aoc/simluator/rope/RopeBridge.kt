package io.github.tomplum.aoc.simluator.rope

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

/**
 * Represents a rope bridge whose movement can be
 * simulated by a [RopeBridgeSimulator].
 *
 * @param knotQuantity The number of knots to tie in the rope
 */
class RopeBridge(private val knotQuantity: Int = 1) : AdventMap2D<RopeSegment>() {
    
    init {
        val knotMarkers = (0..knotQuantity).map { number -> number }
        addTile(Point2D.origin(), RopeSegment(knotMarkers))
    }

    val visited = mutableSetOf(Point2D.origin())

    /**
     * Moves the head of the rop in the given [direction]
     * by exactly one unit of distance.
     * @param direction The direction in which to move the rope.
     */
    fun move(direction: Direction) {
        // The knot leading the charge
        var leaderPos = getHeadPosition()

        // Head moves first
        leaderPos = leaderPos.shift(direction)
        updateRopeSegment(leaderPos, 0)

        // (1-n) the trailing knots that follow...
        (1..knotQuantity).forEach { knotIndex ->
            var knotPos = getKnotPosition(knotIndex)

            if (!knotPos.isOrthogonallyAdjacentTo(leaderPos) && leaderPos != knotPos) {
                val xRelativeDirectionCandidate = leaderPos.xRelativeDirection(knotPos)
                val yRelativeDirectionCandidate = leaderPos.yRelativeDirection(knotPos)

                if (xRelativeDirectionCandidate != null) {
                    knotPos = knotPos.shift(xRelativeDirectionCandidate.first)
                }

                if (yRelativeDirectionCandidate != null) {
                    knotPos = knotPos.shift(yRelativeDirectionCandidate.first)
                }

                if (xRelativeDirectionCandidate == null && yRelativeDirectionCandidate == null) {
                    throw IllegalStateException("Can't find the relative direction of the leading segment so the knot can catch up")
                }
            }

            updateRopeSegment(knotPos, knotIndex)

            leaderPos = knotPos

            if (knotIndex == knotQuantity) {
                visited.add(knotPos)
            }
        }
    }

    private fun updateRopeSegment(pos: Point2D, index: Int) {
        if (hasRecorded(pos)) {
            val existingSegmentMarkers = getTile(pos).markers
            addTile(pos, RopeSegment(existingSegmentMarkers + index))
        } else {
            addTile(pos, RopeSegment(listOf(index)))
        }
    }

    private fun getHeadPosition(): Point2D {
        val headCandidates = filterTiles { tile -> tile.isHead() }.entries
        return if (headCandidates.isNotEmpty()) {
            val head = headCandidates.first()
            val position = head.key
            removeKnot(position, 0)
            position
        } else {
            throw IllegalStateException("Cannot find HEAD position")
        }
    }

    private fun getKnotPosition(index: Int): Point2D {
        val segmentCandidates = filterTiles { tile -> tile.isKnot(index) }.entries
        return if (segmentCandidates.isNotEmpty()) {
            val tail = segmentCandidates.first()
            val position = tail.key
            removeKnot(position, index)
            position
        } else throw IllegalArgumentException("Cannot find KNOT [$index]")
    }

    private fun removeKnot(pos: Point2D, index: Int) {
        if (hasRecorded(pos)) {
            val segment = removeTile(pos)!!
            if (segment.isKnot(index)) {
                val updatedMarkers = segment.markers - index
                if (updatedMarkers.isNotEmpty()) {
                    addTile(pos, RopeSegment(updatedMarkers))
                }
            } else {
                throw IllegalArgumentException("Rope segment at $pos does not contain KNOT [$index]")
            }
        } else {
            throw IllegalArgumentException("Cannot remove KNOT [$index] because it doesn't exist at $pos")
        }
    }
}