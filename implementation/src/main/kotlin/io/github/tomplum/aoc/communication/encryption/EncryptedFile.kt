package io.github.tomplum.aoc.communication.encryption

/**
 * A file on the hand-held communication device that holds
 * the coordinates of the grove location.
 *
 * Each of the three individual grove ordinates are located
 * at positions 1000, 2000 and 3000 in the file.
 *
 * @param contents The contents of the file
 */
class EncryptedFile(private val contents: List<Int>) {

    /**
     * Decrypts the file using mixing.
     *
     * @param quantity The number of times to mix the [contents]
     * @param decryptionKey The key used to decrypt the file
     * @return The sum of the grove ordinates
     */
    fun decrypt(quantity: Int = 1, decryptionKey: Long = 1): Long {
        /**
         * A collection of the original [contents] values
         * mapped to their respective indexes. This allows
         * for multiple elements that have the same value
         * since they are made distinct by their index.
         */
        val values = contents.mapIndexed { index, value -> index.toLong() to value * decryptionKey }

        /**
         * A collection for tracking the values as they
         * are being mixed.
         */
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

    /**
     * Finds the value of the grove ordinate at the
     * given [position] in the file contents after having
     * been mixed.
     *
     * @param position The position of the grove ordinate
     * @return The value at the normalised position
     */
    private fun List<Long>.toGroveCoordinate(position: Int) = this[((position + this.indexOf(0)) % this.size)]
}