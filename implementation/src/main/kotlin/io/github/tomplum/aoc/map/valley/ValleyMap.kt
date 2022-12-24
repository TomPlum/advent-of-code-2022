package io.github.tomplum.aoc.map.valley

import io.github.tomplum.extension.lcm
import io.github.tomplum.libs.logging.AdventLogger
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.map.AdventMap2D
import io.github.tomplum.libs.math.point.Point2D

class ValleyMap(data: List<String>) : AdventMap2D<ValleyTile>() {

    private var xMin: Int
    private var xMax: Int
    private var yMin: Int
    private var yMax: Int

    init {
        var x = 0
        var y = 0
        data.forEach { row ->
            row.forEach { column ->
                val tile = ValleyTile(listOf(column))
                val position = Point2D(x, y)
                addTile(position, tile)
                x++
            }
            x = 0
            y++
        }

        xMin = xMin()!!
        xMax = xMax()!!
        yMin = yMin()!!
        yMax = yMax()!!
    }

    fun traverseBlizzards(): Int {
        val start = filterTiles { tile -> tile.isClear() }.minBy { (pos, _) -> pos.y }.key
        val goal = filterTiles { tile -> tile.isClear() }.maxBy { (pos, _) -> pos.y }.key
        return traverse(start, goal)
    }

    fun traverseValleyWithSnacks(): Int {
        val start = filterTiles { tile -> tile.isClear() }.minBy { (pos, _) -> pos.y }.key
        val goal = filterTiles { tile -> tile.isClear() }.maxBy { (pos, _) -> pos.y }.key
        val expedition = traverse(start, goal)
        val snackBacktrack = traverse(goal, start)
        return (expedition * 2) + snackBacktrack
    }

    private fun traverse(start: Point2D, goal: Point2D): Int {
        val blizzardTimeLcm = 600//listOf(xMax - 2L, yMax - 2L).lcm().toInt()
        val initialState = filterTiles { it.isClear() }.keys
        AdventLogger.debug("Intitial State \n$this\n")
        val seen = mutableListOf(initialState)
        val clearTiles = (1..blizzardTimeLcm).associateWith {
            // Calculate new blizzard positions
            val currentBlizzardPositions = filterTiles { tile -> tile.hasBlizzard() }
            val newBlizzardPositions = currentBlizzardPositions.flatMap { (pos, tile) ->
                tile.getBlizzardDirections().map { (direction, symbol) ->
                    val newPosition = pos.shift(direction)
                    if (getTile(newPosition, ValleyTile(listOf('.'))).isWall()) {
                        newPosition.getBlizzardSpawnPosition(direction) to symbol
                    } else {
                        newPosition to symbol
                    }
                }
            }

            // Clear existing blizzards
            filterTiles { tile -> tile.hasBlizzard() }.keys.forEach { pos -> addTile(pos, ValleyTile(listOf('.'))) }

            // Move Blizzards
            newBlizzardPositions.groupBy { it.first }.forEach { (newPosition, blizzards) ->
                addTile(newPosition, ValleyTile(blizzards.map { it.second }))
            }

            if (filterTiles { it.isClear() }.keys in seen) {
                AdventLogger.debug("Found $it")
            }

            seen.add(filterTiles { it.isClear() }.keys)
            //AdventLogger.debug("Minute $it\n$this\n")

            filterTiles { tile -> tile.isClear() }.keys
        }.toMutableMap()
        clearTiles[0] = initialState

        //addTile(Point2D(start.x, start.y - 1), ValleyTile(listOf('#')))
        // addTile(Point2D(goal.x, goal.y + 1), ValleyTile(listOf('#')))

        var minutesElapsed = 0
        var clearIndex = 0

        var currentPositions = mutableSetOf<Point2D>()
        currentPositions.add(start)

        while(true) {
            if (goal in currentPositions) {
                return minutesElapsed - 1
            }

            if (clearIndex > blizzardTimeLcm) {
                clearIndex = 0
            }

            // Move Person
            val newPositionCandidates = mutableSetOf<Point2D>()
            val clearAfterBlizzardsMoved = clearTiles[clearIndex]!!
            currentPositions.forEach { position ->
                val adjacentPositions = position.orthogonallyAdjacent().filter { pos -> pos in clearAfterBlizzardsMoved }
                newPositionCandidates.addAll(adjacentPositions)
                if (position in clearAfterBlizzardsMoved) {
                    newPositionCandidates.add(position)
                }
            }

            currentPositions = newPositionCandidates
            //val nextPositions = adjacentPositions.filter { pos -> pos in clearAfterBlizzardsMoved }
            //newPositionCandidates.addAll(nextPositions)

            // Can we wait in any of the current?
            //currentPositions.filter {  }


            //val allCandidates =  currentPositions.toMutableSet() + adjacentPositions

            //val lastPositions =.filter { pos -> pos in clearAfterBlizzardsMoved }

            //currentPositions.addAll(nextPositions)
            //currentPositions.addAll(lastPositions)

            minutesElapsed += 1
            clearIndex += 1
            //AdventLogger.debug("Minute $minutesElapsed\n$this\n")
        }
    }

    private fun Point2D.getBlizzardSpawnPosition(direction: Direction) = when(direction) {
        Direction.RIGHT -> Point2D(xMin + 1, this.y)
        Direction.LEFT -> Point2D(xMax - 1, this.y)
        Direction.UP -> Point2D(this.x, yMin + 1)
        Direction.DOWN -> Point2D(this.x, yMax - 1)
        else -> throw IllegalArgumentException("Blizzards cannot move in [$direction]")
    }
}