import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>, duration: Int): Int {
        var x = 1
        var cycle = 1

        fun checkCycle(): Boolean {
            return cycle == duration
        }

        for (instruction in input) {
            if (instruction.startsWith("addx")) {
                val amount = instruction.split(" ")[1].toInt()
                cycle += 1
                if (checkCycle()) {
                    return x * cycle
                }
                cycle += 1
                x += amount
            } else if (instruction == "noop") {
                cycle += 1
            }
            if (checkCycle()) {
                return x * cycle
            }
        }

        return 0
    }

    fun sumPart1(input: List<String>): Int {
        return intArrayOf(20, 60, 100, 140, 180, 220).sumOf { part1(input, it) }
    }

    fun part2(input: List<String>, width: Int = 40) {
        var x = 1
        var cycle = 1
        val output = StringBuilder(240)

        fun render() {
            val char = if ((cycle % 40 - x-1).absoluteValue <= 1) "#" else "."
            output.append(char)
            if (cycle > 240) return
        }

        for (instruction in input) {
            if (instruction.startsWith("addx")) {
                val amount = instruction.split(" ")[1].toInt()
                render()
                cycle += 1
                render()
                cycle += 1
                x += amount
            } else if (instruction == "noop") {
                render()
                cycle += 1
            }
        }
//        println(output)
        output.toString().chunked(40).forEach { println(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
//    check(part1(testInput, 20) == 420)
//    check(part1(testInput, 60) == 1140)
//    check(part1(testInput, 100) == 1800)
//    check(part1(testInput, 140) == 2940)
//    check(part1(testInput, 180) == 2880)
//    check(part1(testInput, 220) == 3960)
//
//    check(sumPart1(testInput) == 13140)

//    check(part2(testInput) == 45000)
    part2(testInput)
    println()

    val input = readInput("Day10")
//    println(sumPart1(input))
    part2(input)
}
