package io.github.tomplum.aoc.simluator.rope

import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class RopeBridge : AdventMap2D<Knot>() {
    init {
        addTile(Point2D.origin(), Knot('B'))
    }

    val visited = mutableSetOf(Point2D.origin())

    fun move(direction: Direction) {
        var head = getHead()
        var tail = getTail()
        val both = getBoth()
        if (both != null) {
            head = both
            tail = both
        }

        var currentHeadPos = head!!.first
        var currentTailPos = tail!!.first

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
            addTile(currentHeadPos, Knot('B'))
        } else {
            addTile(currentHeadPos, Knot('H'))
            addTile(currentTailPos, Knot('T'))
        }

        visited.add(currentTailPos)
    }

/*    private fun getHead(): Pair<Point3D, Knot> {
        val headCandidates = filterTiles { tile -> tile.isHead() }.entries
        if (headCandidates.isEmpty()) {
            val bothCandidates =  filterTiles { tile -> tile.isBoth() }.entries
            if (bothCandidates.isNotEmpty()) {
                return bothCandidates.first().toPair()
            }
        } else {
            return headCandidates.first().toPair()
        }
        throw IllegalStateException("Cannot find rope head")
    }*/

    private fun getHead(): Pair<Point2D, Knot>? {
        val headCandidates = filterTiles { tile -> tile.isHead() }.entries
        return if (headCandidates.isNotEmpty()) {
            val head = headCandidates.first()
            Pair(head.key, removeTile(head.key)!!)
        } else null
    }

    private fun getBoth(): Pair<Point2D, Knot>? {
        val bothCandidates = filterTiles { tile -> tile.isBoth() }.entries
        return if (bothCandidates.isNotEmpty()) {
            val both = bothCandidates.first()
            Pair(both.key, removeTile(both.key)!!)
        } else null
    }

    private fun getTail(): Pair<Point2D, Knot>? {
        val tailCandidates = filterTiles { tile -> tile.isTail() }.entries
        return if (tailCandidates.isNotEmpty()) {
            val tail = tailCandidates.first()
            Pair(tail.key, removeTile(tail.key)!!)
        } else null
    }
}