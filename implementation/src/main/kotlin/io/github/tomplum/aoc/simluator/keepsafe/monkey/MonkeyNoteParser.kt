package io.github.tomplum.aoc.simluator.keepsafe.monkey

import java.util.*
import io.github.tomplum.aoc.simluator.keepsafe.KeepAway

class MonkeyNoteParser {
    /**
     * Parses a list of notes about the [KeepAway] game
     * and produce a list of [Monkey]s that are playing
     * the game.
     *
     * @param notes A list of notes that describe the state of the monkeys
     * @return A list of monkey participants that form a [MonkeyTroop]
     */
    fun parse(notes: List<String>) = notes.fold(mutableListOf<MutableList<String>>(mutableListOf())) { monkeys, line ->
        if (line.isNotBlank()) {
            monkeys.last().add(line)
        } else {
            monkeys.add(mutableListOf())
        }
        monkeys
    }.map { lines ->
        val id = lines[0].removePrefix("Monkey ").first().toString().toInt()
        val startingItems = lines[1].trim()
            .removePrefix("Starting items: ").split(", ")
            .map { item -> item.toLong() }
            .reversed()
            .fold(Stack<Long>()) { stack, item ->
                stack.add(item)
                stack
            }

        val operation = MonkeyOperation(lines[2])
        val test = MonkeyTest(lines.subList(3, 6))
        Monkey(id, startingItems, operation, test)
    }
}