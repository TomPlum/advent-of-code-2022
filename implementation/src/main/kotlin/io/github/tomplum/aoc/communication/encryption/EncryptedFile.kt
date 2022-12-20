package io.github.tomplum.aoc.communication.encryption

import io.github.tomplum.libs.logging.AdventLogger
import kotlin.math.abs

class EncryptedFile(contents: List<Int>) {

    private val values = contents.mapIndexed { index, value -> index to value }
    private var mixed = values.toMutableList()

    init {
        //contents.forEachIndexed { index, value -> values[index] = value }
        //contents.forEachIndexed { index, value -> mixed[index] = value }
    }

    fun mix(): Int {
        AdventLogger.debug("Initial arrangement:\n$values")
        values.forEach { (index, value) ->
           // if (value != 0) {
                val i = mixed.indexOf(Pair(index, value))
                mixed.removeAt(i)
                mixed.add((i + value).mod(mixed.size), Pair(index, value))
               /* val targetIndex = (mixed.indexOf(value) + value)
                val normalisedIndex = if (targetIndex < 0) targetIndex.toFileIndexFromMinus() else targetIndex.toFileIndex()
                //AdventLogger.debug("$value moves between ${mixed[targetIndex]} and ${mixed[if (targetIndex + 1 > mixed.size) 0 else targetIndex + 1]}")
                mixed.remove(value)
                mixed.insertAt(normalisedIndex, value)*/
                //AdventLogger.debug("$mixed\n")
           // }
        }

        val mixedValues = mixed.map { it.second }
        //return listOf(1000, 2000, 3000).sumOf { value -> mixedValues.toGroveCoordinate(value) }
        return mixed.map { it.second }.let {
            val idx0 = it.indexOf(0)
            it[(1000 + idx0) % mixed.size] + it[(2000 + idx0) % mixed.size] + it[(3000 + idx0) % mixed.size]
        }
        //return listOf(1000, 2000, 3000).map { coord -> coord.toFileIndex() }.sumOf { index -> mixedValues[index] }
    }

    private fun List<Int>.toGroveCoordinate(value: Int) = (value + this.indexOf(0)) % this.size

   /* private fun IntArray.insertAt(index: Int, key: Int) {
        val result = this.toMutableList()
        result.add(index, key)
        mixed = result.toIntArray();
    }

    private fun IntArray.remove(value: Int) {
        mixed = this.toMutableList().filterNot { it == value }.toIntArray()
    }*/

    private fun Int.toFileIndex() = if (this <= values.lastIndex) this else this % values.lastIndex

    private fun Int.toFileIndexFromMinus(): Int {
        if (abs(this) <= values.lastIndex) {
            return values.lastIndex - (abs(this) - 1) - 1
        } else {
            return (abs(this) % values.size) - (abs(this) - 1) - 1
        }
    }

}