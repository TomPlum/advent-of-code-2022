package io.github.tomplum.aoc.game.riddle

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
            Yell(operation.trim().toLong())
        }

        name to Monkey(name, action, dependencies)
    }

    fun solve(): Long {
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

    data class Monkey(val name: String, private val equation: Equation, val dependencies: Pair<String, String>?) {
        var first: Long = -1
        var second: Long = -1

        fun canYell() = equation is Yell || (first != -1L && second != -1L)

        fun yell(): Long {
            if (canYell()) {
                return equation.solve(first, second)
            }
            throw IllegalStateException("Monkey $name cannot yell as it doesn't have both values")
        }
    }

    interface Equation {
        fun solve(a: Long, b: Long): Long
    }

    inner class Add(val aDependent: String, val bDependent: String) : Equation {
        override fun solve(a: Long, b: Long): Long {
            return a + b
        }
    }

    inner class Subtract(val aDependent: String, val bDependent: String) : Equation {
        override fun solve(a: Long, b: Long): Long {
            return a - b
        }
    }

    inner class Divide(val aDependent: String, val bDependent: String) : Equation {
        override fun solve(a: Long, b: Long): Long {
            return a / b
        }
    }

    inner class Multiply(val aDependent: String, val bDependent: String) : Equation {
        override fun solve(a: Long, b: Long): Long {
            return a * b
        }
    }

    inner class Yell(private val value: Long) : Equation {
        override fun solve(a: Long, b: Long): Long {
            return value
        }
    }
}