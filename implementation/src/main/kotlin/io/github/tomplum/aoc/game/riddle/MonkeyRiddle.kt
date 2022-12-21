package io.github.tomplum.aoc.game.riddle

import kotlin.math.abs

class MonkeyRiddle(jobs: List<String>) {

    private val monkeys = jobs.associate { job ->
        val parts = job.split(":")
        val name = parts.first()
        val operation = parts[1].trim()
        var dependencies: Pair<String, String>? = null
        val action = if (operation.contains("+")) {
            val values = operation.split(" + ")
            dependencies = Pair(values[0], values[1])
            Add(values[0], values[1])
        } else if (operation.contains("-")) {
            val values = operation.split(" - ")
            dependencies = Pair(values[0], values[1])
            Subtract(values[0], values[1])
        } else if (operation.contains("*")) {
            val values = operation.split(" * ")
            dependencies = Pair(values[0], values[1])
            Multiply(values[0], values[1])
        } else if (operation.contains("/")) {
            val values = operation.split(" / ")
            dependencies = Pair(values[0], values[1])
            Divide(values[0], values[1])
        } else {
            val value = if (name == "humn") 2037 else operation.trim().toLong()
            Yell(value)
        }

        name to Monkey(name, action, dependencies)
    }

    private val mathMonkeys = jobs.associate { job ->
        val parts = job.split(": ")
        val name = parts.first()
        val operation = parts[1].trim().split(" ")
        val monkey = if (operation.size == 1)  {
            val constant = if (name == "humn") "x" else operation[0]
            MathMonkey(name, constant, "", "", "")
        } else {
            val operator = if (name == "root") "=" else operation[1]
            MathMonkey(name, "", operation[0], operation[2], operator)
        }
        name to monkey
    }

    fun solve(): Long {
        find(monkeys["root"]!!)
        val root = monkeys["root"]!!
        val initialHumanValue = (monkeys["humn"]!!.equation as Yell).value
        var closest = Pair(initialHumanValue, root.first)
        val answer = root.second
        var lastGuess = initialHumanValue
        while (true) {
            val guess = lastGuess + 10000
            lastGuess = guess
            (monkeys["humn"]!!.equation as Yell).value = guess
            find(monkeys["root"]!!)
            val newRoot = monkeys["root"]!!
            val closestCandidate = newRoot.first
            if (abs(closestCandidate - answer) < abs(closest.second - answer)) {
                closest = Pair(guess, closestCandidate)
            }

            if (closestCandidate == answer) {
                return guess
            }
        }
        return answer
    }

    fun solve2(): Long {
        monkeys["root"]!!.equation = Equals()

        val equation = buildEquation("root")
        return find(monkeys["root"]!!)
    }

    private fun find(source: Monkey): Long {
        if(!source.canYell()) {
            val deps = source.dependencies
            if (deps != null) {
                val firstDependentMonkey = monkeys[deps.first]!!
                if (!firstDependentMonkey.canYell()) {
                    source.first = find(firstDependentMonkey)
                } else {
                    source.first = firstDependentMonkey.yell()
                }

                val secondDependentMonkey = monkeys[deps.second]!!
                if (!secondDependentMonkey.canYell()) {
                    source.second = find(secondDependentMonkey)
                } else {
                    source.second = secondDependentMonkey.yell()
                }
            }
        }

        return source.yell()
    }

    private fun buildEquation(source: String): String {
        val monkey = mathMonkeys[source]!!
        if (monkey.number.isNotBlank() && monkey.number.all { it.isDigit() }) {
            return monkey.number
        }

        if (monkey.number == "x") {
            return monkey.number
        }

        monkey!!.first = buildEquation(monkey.first)
        monkey.second = buildEquation(monkey.second)

        return "(${monkey.first}${monkey.operator}${monkey.second})"
    }

    data class Monkey(val name: String, var equation: Equation, val dependencies: Pair<String, String>?) {
        var first: Long = -1
        var second: Long = -1
        var equationString =  ""

        fun canYell() = equation is Yell || (first != -1L && second != -1L)

        fun yell(): Long {
            if (canYell()) {
                return equation.solve(first, second)
            }
            throw IllegalStateException("Monkey $name cannot yell as it doesn't have both values")
        }
    }

    data class MathMonkey(val name: String, val number: String, var first: String, var second: String, val operator: String)

    interface Equation {
        val op: String
        fun solve(a: Long, b: Long): Long
    }

    inner class Add(val aDependent: String, val bDependent: String) : Equation {
        override val op: String
            get() = "+"
        override fun solve(a: Long, b: Long): Long {
            return a + b
        }
    }

    inner class Subtract(val aDependent: String, val bDependent: String) : Equation {
        override val op: String
            get() = "-"

        override fun solve(a: Long, b: Long): Long {
            return a - b
        }
    }

    inner class Divide(val aDependent: String, val bDependent: String) : Equation {
        override val op: String
            get() = "/"

        override fun solve(a: Long, b: Long): Long {
            return a / b
        }
    }

    inner class Multiply(val aDependent: String, val bDependent: String) : Equation {
        override val op: String
            get() = "*"

        override fun solve(a: Long, b: Long): Long {
            return a * b
        }
    }

    inner class Equals : Equation {
        override val op: String
            get() = "="

        override fun solve(a: Long, b: Long): Long {
            return if (a == b) 1 else 0
        }

    }

    inner class Yell(var value: Long) : Equation {
        override val op: String
            get() = ""

        override fun solve(a: Long, b: Long): Long {
            return value
        }
    }
}