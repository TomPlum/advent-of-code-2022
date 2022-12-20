package io.github.tomplum.aoc.communication.encryption

class EncryptedFile(private val contents: List<Int>) {

    fun decrypt(quantity: Int = 1, decryptionKey: Long = 1): Long {
        val values = contents.mapIndexed { index, value -> index.toLong() to value * decryptionKey }
        val mixed = values.toMutableList()

        repeat(quantity) {
            values.forEach { iv ->
                val i = mixed.indexOf(iv)
                mixed.removeAt(i)
                mixed.add((i + iv.second).mod(mixed.size), iv)
            }
        }

        val mixedValues = mixed.map { iv -> iv.second }
        return listOf(1000, 2000, 3000).sumOf { value -> mixedValues.toGroveCoordinate(value) }
    }

    private fun List<Long>.toGroveCoordinate(value: Int) = this[((value + this.indexOf(0)) % this.size)]
}