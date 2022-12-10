package io.github.tomplum.aoc.communication.cpu

data class RegisterSnapshot(val xRegister: Int, val cycle: Int) {
    companion object {
        fun initial() = RegisterSnapshot(xRegister = 1, cycle = 1)
    }
}