package io.github.tomplum.aoc.simluator.tree

import io.github.tomplum.aoc.map.seed.GroveMap
import io.github.tomplum.aoc.map.seed.GrovePatch
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.Direction.DOWN
import io.github.tomplum.libs.math.point.Point2D
import java.util.*
import kotlin.collections.ArrayDeque

class StarFruitSimulator(scan: List<String>) {

    private val map = GroveMap(scan)

    fun findElvenBoundingRectangle(rounds: Int = 10): Int {
        var round = 1
        val directionProposalRotation = DirectionProposalRotation()

        while(round <= rounds) {
            val movementProposals = mutableMapOf<Point2D, Point2D>()

            // During the first half of each round, each Elf considers
            // the eight positions adjacent to themself.
            map.getElfPositions().forEach position@{ currentPos ->
                val adjacent = map.getAdjacentTiles(currentPos).values.toList().filterNotNull()

                // If no other Elves are in one of those eight positions,
                // the Elf does not do anything during this round.
                if (adjacent.count { patch -> patch.containsElf() } == 0) {
                    return@position
                }

                // Otherwise, the Elf looks in each of four directions in
                // the following order and proposes moving one step in the
                // first valid direction:
                for (proposedDirection in directionProposalRotation.values) {
                    val proposedPosition = proposedDirection.check(currentPos, adjacent)
                    if (proposedPosition.isPresent) {
                        movementProposals[currentPos] = proposedPosition.get()
                        break
                    }
                }
            }

            // After each Elf has had a chance to propose a move, the second half of the round can begin.
            // Simultaneously, each Elf moves to their proposed destination tile if they were the only Elf to
            // propose moving to that position. If two or more Elves propose moving to the same position,
            // none of those Elves move.
            val proposalCounts = movementProposals.values.groupingBy { it }.eachCount()
            movementProposals.filterValues { proposed -> proposalCounts[proposed] == 1  }.forEach { (current, proposed) ->
                map.moveElf(current, proposed)
            }

            // Finally, at the end of the round, the first direction the Elves considered is moved to the
            // end of the list of directions.
            directionProposalRotation.rotate()
            round += 1
        }

        val elfPositions = map.getElfPositions()
        val x1 = elfPositions.minBy { pos -> pos.x }.x
        val y1 = elfPositions.minBy { pos -> pos.y }.y
        val x2 = elfPositions.maxBy { pos -> pos.x }.x
        val y2 = elfPositions.maxBy { pos -> pos.y }.y
        val boundingRectangle = (x1..x2).flatMap { x ->
            (y1..y2).map { y ->
                map.getPatch(Point2D(x, y))
            }
        }

        return boundingRectangle.count { patch -> patch.isEmpty() }
    }

    fun findRoundWithNoMovement(): Int {
        var round = 1
        val directionProposalRotation = DirectionProposalRotation()

        var roundPassedWithNoMovement = false

        while(!roundPassedWithNoMovement) {
            val movementProposals = mutableMapOf<Point2D, Point2D>()

            // During the first half of each round, each Elf considers
            // the eight positions adjacent to themself.
            map.getElfPositions().forEach position@{ currentPos ->
                val adjacent = map.getAdjacentTiles(currentPos).values.toList().filterNotNull()

                // If no other Elves are in one of those eight positions,
                // the Elf does not do anything during this round.
                if (adjacent.count { patch -> patch.containsElf() } == 0) {
                    return@position
                }

                // Otherwise, the Elf looks in each of four directions in
                // the following order and proposes moving one step in the
                // first valid direction:
                for (proposedDirection in directionProposalRotation.values) {
                    val proposedPosition = proposedDirection.check(currentPos, adjacent)
                    if (proposedPosition.isPresent) {
                        movementProposals[currentPos] = proposedPosition.get()
                        break
                    }
                }
            }

            if (movementProposals.isEmpty()) {
                return round
            }

            // After each Elf has had a chance to propose a move, the second half of the round can begin.
            // Simultaneously, each Elf moves to their proposed destination tile if they were the only Elf to
            // propose moving to that position. If two or more Elves propose moving to the same position,
            // none of those Elves move.
            val proposalCounts = movementProposals.values.groupingBy { it }.eachCount()
            movementProposals.filterValues { proposed -> proposalCounts[proposed] == 1  }.forEach { (current, proposed) ->
                map.moveElf(current, proposed)
            }

            // Finally, at the end of the round, the first direction the Elves considered is moved to the
            // end of the list of directions.
            directionProposalRotation.rotate()
            round += 1
        }

        return round
    }

    inner class DirectionProposalRotation {
        val values = ArrayDeque<DirectionProposal>()

        init {
            values.addFirst(EasternProposal())
            values.addFirst(WesternProposal())
            values.addFirst(SouthernProposal())
            values.addFirst(NorthernProposal())
        }

        fun rotate() {
            val top = values.removeFirst()
            values.addLast(top)
        }
    }

    interface DirectionProposal {
        /**
         * Adjacent patch order:
         * [0] S
         * [1] E
         * [2] N
         * [3] W
         * [4] NW
         * [5] NE
         * [6] SE
         * [7] SW
         */
        fun check(current: Point2D, adjacent: List<GrovePatch>): Optional<Point2D>
    }

    inner class NorthernProposal: DirectionProposal {
        /**
         * If there is no Elf in the N, NE, or NW adjacent positions,
         * the Elf proposes moving north one step.
         */
        override fun check(current: Point2D, adjacent: List<GrovePatch>): Optional<Point2D> {
            if (adjacent[2].isEmpty() && adjacent[5].isEmpty() && adjacent[4].isEmpty()) {
                return Optional.of(current.shift(DOWN))
            }

            return Optional.empty()
        }
    }

    inner class EasternProposal: DirectionProposal {
        /**
         * If there is no Elf in the E, NE, or SE adjacent positions,
         * the Elf proposes moving east one step.
         */
        override fun check(current: Point2D, adjacent: List<GrovePatch>): Optional<Point2D> {
            if (adjacent[1].isEmpty() && adjacent[5].isEmpty() && adjacent[6].isEmpty()) {
                return Optional.of(current.shift(Direction.RIGHT))
            }

            return Optional.empty()
        }
    }

    inner class SouthernProposal: DirectionProposal {
        /**
         * If there is no Elf in the S, SE, or SW adjacent positions,
         * the Elf proposes moving south one step.
         */
        override fun check(current: Point2D, adjacent: List<GrovePatch>): Optional<Point2D> {
            if (adjacent[0].isEmpty() && adjacent[6].isEmpty() && adjacent[7].isEmpty()) {
                return Optional.of(current.shift(Direction.UP))
            }

            return Optional.empty()
        }
    }

    inner class WesternProposal: DirectionProposal {
        /**
         * If there is no Elf in the W, NW, or SW adjacent positions,
         * the Elf proposes moving west one step.
         */
        override fun check(current: Point2D, adjacent: List<GrovePatch>): Optional<Point2D> {
            if (adjacent[3].isEmpty() && adjacent[4].isEmpty() && adjacent[7].isEmpty()) {
                return Optional.of(current.shift(Direction.LEFT))
            }

            return Optional.empty()
        }
    }
}