package io.github.tomplum.aoc.communication.encryption

class EncryptedFile(private val contents: List<Int>) {

    fun decrypt(quantity: Int = 1, decryptionKey: Long = 1): Long {
        val values = contents.mapIndexed { index, value -> index.toLong() to value * decryptionKey }
        var mixed = values.toMutableList()

        repeat(quantity) {
            values.forEach { (index, value) ->
                val i = mixed.indexOf(Pair(index, value))
                mixed.removeAt(i)
                mixed.add((i + value).mod(mixed.size), Pair(index, value))
            }
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
}