package io.github.tomplum.aoc.sorting.crate

import java.util.Stack

class CrateArranger(private val stackDrawing: List<String>) {
    fun consolidate(): String {
        val ( crateStacks, instructions ) = parseDrawing()
        instructions.forEach { instruction ->
            repeat(instruction.quantity) {
                val crate = crateStacks[instruction.from].pop()
                crateStacks[instruction.to].push(crate)
            }
        }

        return crateStacks.map { stack -> stack.peek() }.joinToString("")
    }

    private fun parseDrawing(): Pair<List<Stack<Char>>, List<Instruction>> {
        val crates = mutableListOf<String>()
        val instructions = mutableListOf<String>()
        var finishedCrates = false
        stackDrawing.forEach { line ->
            if (line.isEmpty()) {
                finishedCrates = true
                return@forEach
            }

            if (!finishedCrates) {
                crates.add(line)
            } else {
                instructions.add(line)
            }
        }

        val stackQuantity = crates.first().length / 3
        val stacks = (1..stackQuantity).map { Stack<Char>() }
        crates.reversed().drop(1).forEach row@{ row ->
            var targetStack = 1
            var lastWasStackEnd = false
            var inBetweenStacks = false
            row.forEach { char ->
                if (char.isAlpha()) {
                    stacks[targetStack - 1].add(char)
                }

                if (char == '[') {
                    inBetweenStacks = false
                    lastWasStackEnd = false
                }

                if (char == ']') {
                    lastWasStackEnd = true
                    return@forEach
                }

                if (char == ' ' && !inBetweenStacks) {

                    if (lastWasStackEnd) {
                        targetStack++
                        lastWasStackEnd = false
                        return@forEach
                    }

                    inBetweenStacks = true
                    targetStack++
                }
            }
        }

        val parsedInstructions = instructions.map { line ->
            val numbers = line.filter { !it.isAlpha() && it != ' ' }.map { it.toString().toInt() }
            Instruction(numbers[0], numbers[1] - 1, numbers[2] - 1)
        }

        return Pair(stacks, parsedInstructions)
    }

    private data class Instruction(val quantity: Int, val from: Int, val to: Int)

    private fun Char.isAlpha() = this.code in 97..122 || this.code in 65..90
}