sealed class Operation {
    data class Add(val value: Int): Operation() {
        override fun operate(on: Int): Int {
            return value + on
        }
    }
    data class Multiply(val value: Int): Operation() {
        override fun operate(on: Int): Int {
            return value * on
        }
    }
    object Squared: Operation() {
        override fun operate(on: Int): Int {
            return on * on
        }
    }

    abstract fun operate(on: Int): Int

    companion object {
        fun parse(input: String): Operation {
            val str = input.removePrefix("  Operation: new = ")
            return if (str == "old * old") {
                Squared
            } else if (str.contains("*")) {
                val value = str.split(" * ")[1].toInt()
                Multiply(value)
            } else {
                val value = str.split(" + ")[1].toInt()
                Add(value)
            }
        }
    }
}

sealed class Test {
    data class Divisible(override val value: Int): Test() {
        override fun test(worryUnderTest: Int): Boolean {
            return worryUnderTest % value == 0
        }
    }

    abstract fun test(worryUnderTest: Int): Boolean
    abstract val value: Int

    companion object {
        fun parse(input: String): Test? {
            val str = input.removePrefix("  Test: ")
            return if (str.startsWith("divisible by")) {
                val value = str.split(" ").last().toInt()
                Divisible(value)
            } else {
                null
            }
        }
    }
}

data class Destinations(val success: Int, val failure: Int) {
    companion object {
        operator fun invoke(input: List<String>) : Destinations {
            val trueDest = input[0].split(" ").last().toInt()
            val falseDest = input[1].split(" ").last().toInt()
            return Destinations(trueDest, falseDest)
        }
    }
}

data class Monkey(
    var items: MutableList<Int>,
    val operation: Operation,
    val test: Test,
    val destinations: Destinations,
    var inspectionCount: Long = 0
) {
    companion object {
        operator fun invoke(input: List<String>): Monkey {
            val items = input[1].substring(18).split(", ").map { it.toInt() }.toMutableList()
            val operation = Operation.parse(input[2])
            val test = Test.parse(input[3])!!
            val destinations = Destinations(input.subList(4, 6))
            return Monkey(items, operation, test, destinations)
        }
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        val monkeys = input.chunked(7).map { Monkey(it) }

        for (i in 0 until 20) {
            for (monkey in monkeys) {
                monkey.items = monkey.items.map { monkey.operation.operate(it) / 3 }.toMutableList()
                monkey.inspectionCount += monkey.items.size
                val iter = monkey.items.iterator()
                while (iter.hasNext()) {
                    val item = iter.next()
                    val destination =
                        if (monkey.test.test(item)) monkey.destinations.success else monkey.destinations.failure
                    monkeys[destination].items.add(item)
                    iter.remove()
                }
            }
        }
//        println(monkeys)
        val mostActive = monkeys.sortedByDescending { it.inspectionCount }
        return mostActive[0].inspectionCount * mostActive[1].inspectionCount
    }

    fun part2(input: List<String>): Long {
        val monkeys = input.chunked(7).map { Monkey(it) }

        val modulo = monkeys.map { it.test.value } .fold(1) { acc, iter -> acc * iter }

        for (i in 0 until 1000) {
            for (monkey in monkeys) {
                monkey.items.map { monkey.operation.operate(it) }.forEach {
                    val destination = if (monkey.test.test(it)) monkey.destinations.success else monkey.destinations.failure
                    monkeys[destination].items.add(it % modulo)
                    monkey.inspectionCount += 1
                }
                monkey.items = mutableListOf()
            }
        }
//        println(monkeys)
        val mostActive = monkeys.sortedByDescending { it.inspectionCount }
        return mostActive[0].inspectionCount * mostActive[1].inspectionCount
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158L)

    val input = readInput("Day11")
    println(part1(input))
//    println(part2(input))
}
