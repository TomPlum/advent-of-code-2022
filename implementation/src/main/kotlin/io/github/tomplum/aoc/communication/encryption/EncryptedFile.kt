package io.github.tomplum.aoc.communication.encryption

class EncryptedFile(private val contents: List<Int>) {

    fun decrypt(quantity: Int = 1, decryptionKey: Long = 1): Long {
        val values = contents.mapIndexed { index, value -> index.toLong() to value * decryptionKey }
        val mixed = values.toMutableList()

        repeat(quantity) {
            values.forEach { source ->
                val currentValueIndex = mixed.indexOf(source)
                mixed.removeAt(currentValueIndex)

                val targetIndex = currentValueIndex + source.second
                val shiftedIndex = targetIndex.mod(mixed.size)
                mixed.add(shiftedIndex, source)
            }
        }

        return listOf(1000, 2000, 3000).sumOf { value -> mixed.map { iv -> iv.second }.toGroveCoordinate(value) }
    }

    private fun List<Long>.toGroveCoordinate(value: Int) = this[((value + this.indexOf(0)) % this.size)]
}