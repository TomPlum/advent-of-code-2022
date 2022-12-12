package io.github.tomplum.aoc.map.tree

class TreeGridObserver(heightMapData: List<String>) {

    private val treeMap = TreeMap(heightMapData)

    fun countTreesVisibleFromOutside(): Int {
        return treeMap.getTreesVisibleFromOutside().count()
    }

    fun findHighestPossibleScenicScore(): Int {
        return treeMap.getTreeScenicScores().max()
    }
}