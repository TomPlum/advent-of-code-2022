package io.github.tomplum.aoc.sorting.crate

import io.github.tomplum.libs.logging.AdventLogger
import java.util.Stack

class CrateArranger(private val stackDrawing: List<String>) {
    fun consolidate(): String {
        val ( crateStacks, instructions ) = parseDrawing()
        crateStacks.forEachIndexed { i, stack ->
            AdventLogger.debug("${i + 1} ${stack.joinToString(" ") { "[$it]" }}")
        }
        instructions.forEach { instruction ->
            AdventLogger.debug("Executing instruction: move ${instruction.quantity} from ${instruction.from + 1} to ${instruction.to}")
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

        val s = "[D] [H] [L] [N] [N] [M] [D] [D] [B]"
        val stackQuantity = (crates.first().length / 3)
        val stacks = (1..stackQuantity).map { Stack<Char>() }

        crates.reversed().drop(1).forEach { row ->
            var n = 1
            var indices = mutableListOf(n)
            while (n + 4 <= row.length) {
                n += 4
                indices.add(n)
            }

            indices.map { i -> row[i] }.forEachIndexed { i, crate ->
                if (crate != ' ') stacks[i].add(crate)
            }
        }


        /*val stackQuantity = (crates.first().length / 3)
        val stacks = (1..stackQuantity).map { Stack<Char>() }
        crates.reversed().drop(1).forEach row@{ row ->
            var targetStack = 1
            var lastWasStackEnd = false
            var inBetweenStacks = false
            var emptyCount = 0
            row.forEach { char ->
                if (char.isAlpha()) {
                    stacks[targetStack - 1].add(char)
                }

                if (char == '[') {
                    emptyCount = 0
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

                    // Only count for the three chars where [X] are
                    emptyCount++

                    if (emptyCount == 3) {
                        // If we've seen 5 empties (in place of " [X] "), we're on another empty column
                        targetStack++
                        emptyCount = 0
                    }

                    inBetweenStacks = true
                    //targetStack++
                }
            }
        }*/

        val parsedInstructions = instructions.map { line ->
            val quantity = line.substringAfter("move ").substringBefore(" from").toInt()
            val sourceStackIndex = line.substringAfter("from ").substringBefore(" to").toInt() - 1
            val targetStackIndex = line.substringAfter("to ").trim().toInt() - 1
            Instruction(quantity, sourceStackIndex, targetStackIndex)
        }

        return Pair(stacks.filter { it.isNotEmpty() }, parsedInstructions)
    }

    private data class Instruction(val quantity: Int, val from: Int, val to: Int)

    private fun Char.isAlpha() = this.code in 97..122 || this.code in 65..90
}