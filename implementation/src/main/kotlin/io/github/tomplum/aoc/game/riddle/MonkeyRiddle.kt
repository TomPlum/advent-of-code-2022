package io.github.tomplum.aoc.game.riddle

import com.fathzer.soft.javaluator.DoubleEvaluator
import com.fathzer.soft.javaluator.Parameters
import com.fathzer.soft.javaluator.StaticVariableSet

class MonkeyRiddle(jobs: List<String>) {

    private val monkeys = jobs.map { job ->
        val split = job.split(": ")
        val name = split.first()
        val action = split[1]
        if (action.all { char -> char.isDigit() }) {
            Monkey(name).apply {
                number = action.toLong()
            }
        } else {
            Monkey(name).apply {
                val parts = action.split(" ")
                equation = Equation(parts[1].first())
                dependencies += listOf(parts[0], parts[2])
            }
        }
    }.associateBy { monkey -> monkey.name }

    private val mathMonkeys = jobs
        .map { job -> MathMonkey.fromJobString(job) }
        .associateBy { monkey -> monkey.name }

    fun numberYelledByRootMonkey(): Long {
        return solveMonkeyEquation(monkeys["root"]!!)
    }

    fun numberYelledByUs(): Long {
        val equation = createEquation("root")
        val variables = StaticVariableSet<Double>()
        val result = DoubleEvaluator().evaluate(equation)
        return 0
    }

    private fun solveMonkeyEquation(source: Monkey): Long {
        val deps = source.dependencies

        if (deps.isEmpty()) {
            return source.yell()
        }

        val first = monkeys[deps.first()]!!
        if (!first.canYell()) {
            source.left = solveMonkeyEquation(first)
        } else {
            source.left = first.yell()
        }

        val second = monkeys[deps.last()]!!
        if (!second.canYell()) {
            source.right = solveMonkeyEquation(second)
        } else {
            source.right = second.yell()
        }

        return source.yell()
    }

    private fun createEquation(source: String): String {
        val monkey = mathMonkeys[source]!!
        if (monkey.number.isNotBlank() && monkey.number.all { it.isDigit() }) {
            return monkey.number
        }

        if (monkey.number == "x") {
            return monkey.number
        }

        monkey.first = createEquation(monkey.first)
        monkey.second = createEquation(monkey.second)

        return "(${monkey.first}${monkey.operator}${monkey.second})"
    }

    data class Monkey(val name: String, ) {
        var left: Long = -1
        var right: Long = -1
        var number = -1L
        var equation: Equation? = null
        val dependencies = mutableListOf<String>()

        fun canYell() = equation != null && (left != -1L && right != -1L)

        fun yell(): Long {
            return if (number != -1L) {
                number
            } else {
                equation!!.solve(left, right)
            }
        }
    }

    data class MathMonkey(val name: String, val number: String, var first: String, var second: String, val operator: String) {
        companion object {
            fun fromJobString(job: String): MathMonkey {
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
                return monkey
            }
        }
    }

    class Equation(private val operator: Char) {
        fun solve(a: Long, b: Long) = when(operator) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> a / b
            else -> throw IllegalArgumentException("Unknown Equation Operation [$operator]")
        }
    }
}