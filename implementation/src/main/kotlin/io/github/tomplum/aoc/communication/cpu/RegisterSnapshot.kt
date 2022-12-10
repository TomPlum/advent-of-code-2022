package io.github.tomplum.aoc.communication.cpu

/**
 * A snapshot of the data in-memory during a single cycle
 * of a [ClockCircuit].
 * @param xRegister The value of the x register at the [cycle]
 * @param cycle The CPU cycle in which the snapshot was captured
 */
data class RegisterSnapshot(val xRegister: Int, val cycle: Int) {
    companion object {
        /**
         * Produces the initial snapshot of a [ClockCircuit] buffer.
         * The x register initial value is 1.
         * The CPU cycles start at 1.
         */
        fun initial() = RegisterSnapshot(xRegister = 1, cycle = 1)
    }
}