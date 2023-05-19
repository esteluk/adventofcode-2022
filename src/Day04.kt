fun main() {

    fun part1(input: List<String>): Int {
        return input.map { elves ->
            val split = elves.split(",").map { range ->
                val rangeValues = range.split("-")
                IntRange(rangeValues[0].toInt(), rangeValues[1].toInt())
            }
            val pair = Pair(split[0], split[1])
            pair.first.contains(pair.second.first) && pair.first.contains(pair.second.last)
                    || pair.second.contains(pair.first.first) && pair.second.contains(pair.first.last)
        }.count { it }

    }


    fun part2(input: List<String>): Int {
        return input.map { elves ->
            val split = elves.split(",").map { range ->
                val rangeValues = range.split("-")
                IntRange(rangeValues[0].toInt(), rangeValues[1].toInt())
            }
            val pair = Pair(split[0], split[1])

            pair.first.intersect(pair.second).isNotEmpty()
        }.count { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
