package io.github.tomplum.aoc.simluator.monkey.parser

import java.util.*

class MonkeyNoteParser {
    fun parse(notes: List<String>) = notes.fold(mutableListOf<MutableList<String>>(mutableListOf())) { monkeys, line ->
        if (line.isNotBlank()) monkeys.last().add(line) else monkeys.add(mutableListOf())
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