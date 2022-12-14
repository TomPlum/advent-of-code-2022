package io.github.tomplum.aoc.map.sand

class ReservoirSimulator(private val reservoir: RegolithReservoir) {
    fun simulate(): Int {
        return reservoir.produceSand()
    }
}