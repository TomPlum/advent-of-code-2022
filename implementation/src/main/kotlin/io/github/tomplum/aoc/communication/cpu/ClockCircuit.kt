package io.github.tomplum.aoc.communication.cpu

class ClockCircuit(private val program: List<String>) {
    private val registerBuffer = Array(program.size * 2) { 0 }

    fun run(): Buffer {
        var xRegister = 1
        registerBuffer[0] = xRegister

        val instructions = program.map { line ->
            when (line.split(" ")[0]) {
                "noop" -> NoOp()
                "addx" -> Add.fromString(line)
                else -> throw IllegalArgumentException("Unknown program instruction [$line]")
            }
        }

        val initialSnapshot = RegisterSnapshot(instructions.first(), 1, 1)
        val bufferSnapshots = instructions.fold(listOf(initialSnapshot)) { snapshots, instruction ->
            snapshots + instruction.execute(snapshots.last())
        }

        return Buffer(bufferSnapshots)
    }
}