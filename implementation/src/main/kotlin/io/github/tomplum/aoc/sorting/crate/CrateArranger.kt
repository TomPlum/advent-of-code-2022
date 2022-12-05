package io.github.tomplum.aoc.sorting.crate

import io.github.tomplum.aoc.sorting.crate.strategy.CrateSortingStrategy
import java.util.Stack

class CrateArranger(private val stackDrawing: List<String>) {
    fun consolidate(strategy: CrateSortingStrategy): String {
        val ( crateStacks, instructions ) = parseDrawing()

        val sortedCrateStacks = strategy.sort(instructions, crateStacks)

        return sortedCrateStacks.map { stack -> stack.peek() }.joinToString("")
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

        val stackQuantity = (crates.first().length / 3)
        val stacks = (1..stackQuantity).map { Stack<Char>() }

        crates.reversed().drop(1).forEach { row ->
            var n = 1
            val indices = mutableListOf(n)
            while (n + 4 <= row.length) {
                n += 4
                indices.add(n)
            }

            indices.map { i -> row[i] }.forEachIndexed { i, crate ->
                if (crate != ' ') stacks[i].add(crate)
            }
        }

        val parsedInstructions = instructions.map { line ->
            val quantity = line.substringAfter("move ").substringBefore(" from").toInt()
            val sourceStackIndex = line.substringAfter("from ").substringBefore(" to").toInt() - 1
            val targetStackIndex = line.substringAfter("to ").trim().toInt() - 1
            Instruction(quantity, sourceStackIndex, targetStackIndex)
        }

        return Pair(stacks.filter { it.isNotEmpty() }, parsedInstructions)
    }
}