package io.github.tomplum.aoc.simluator.rope

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class RopeBridge(private val knotQuantity: Int = 1) : AdventMap2D<RopeSegment>() {
    
    init {
        //addTile(Point2D.origin(), Knot('B'))
        val knotMarkers = (0..knotQuantity).map { number -> number }
        addTile(Point2D.origin(), RopeSegment(knotMarkers))
    }

    val visited = mutableSetOf(Point2D.origin())

    fun move(direction: Direction) {
        var currentHeadPos = getHeadPosition()
        var currentTailPos = getTailPosition()
        val both = getBothPosition()
        if (both != null) {
            currentHeadPos = both
            currentTailPos = both
        }

        currentHeadPos = currentHeadPos.shift(direction)
        if (!currentTailPos.isOrthogonallyAdjacentTo(currentHeadPos) && currentHeadPos != currentTailPos) {
            val xRelativeDirectionCandidate = currentHeadPos.xRelativeDirection(currentTailPos)
            val yRelativeDirectionCandidate = currentHeadPos.yRelativeDirection(currentTailPos)

            if (xRelativeDirectionCandidate != null) {
                currentTailPos = currentTailPos.shift(xRelativeDirectionCandidate.first)
            }

            if (yRelativeDirectionCandidate != null) {
                currentTailPos = currentTailPos.shift(yRelativeDirectionCandidate.first)
            }

            if (xRelativeDirectionCandidate == null && yRelativeDirectionCandidate == null) {
                throw IllegalStateException("Can't find the relative direction of the HEAD so the TAIL can catch up")
            }
        }

        if (currentHeadPos == currentTailPos) {
            addTile(currentHeadPos, RopeSegment(listOf(0 ,1)))
        } else {
            addTile(currentHeadPos, RopeSegment(listOf(0)))
            addTile(currentTailPos, RopeSegment(listOf(1)))
        }

        visited.add(currentTailPos)
    }

    fun moveN(direction: Direction) {
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
                    throw IllegalStateException("Can't find the relative direction of the HEAD so the TAIL can catch up")
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

    private fun getBothPosition(): Point2D? {
        val bothCandidates = filterTiles { tile -> tile.isKnot(0) && tile.isKnot(1) }.entries
        return if (bothCandidates.isNotEmpty()) {
            val both = bothCandidates.first()
            val position = both.key
            removeTile(position)
            position
        } else null
    }

    private fun getTailPosition(): Point2D {
        val tailCandidates = filterTiles { tile -> tile.isKnot(1) }.entries
        return if (tailCandidates.isNotEmpty()) {
            val tail = tailCandidates.first()
            val position = tail.key
            removeTile(position)
            position
        } else {
            throw IllegalStateException("Cannot find TAIL position")
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