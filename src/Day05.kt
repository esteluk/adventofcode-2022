fun main() {

    fun parseInitialState(input: List<String>): Array<String> {
        val rows = input.dropLast(1)
        val stackCount = input.last().trim().split(" ").last().toInt()
        val stacks = Array<String>(stackCount) { "" }
        for (i in rows.lastIndex downTo 0) {
            for (j in 0 until stackCount) {
                if (rows[i].lastIndex >= (j*4)+2) {
                    stacks[j] += rows[i].substring((j * 4) + 1, (j * 4) + 2).trim()
                }
            }
        }
        println(stacks.joinToString())
        return stacks
    }

    fun part1(input: List<String>): String {
        val splitIndex = input.indexOf("")
        val initialStateStrings = input.subList(0, splitIndex)
        val instructions = input.subList(splitIndex+1, input.size)
        val state = parseInitialState(initialStateStrings)

        for (row in instructions) {
//            println(row)
            val count = row.split(" from ").first().split(" ").last().toInt()
            val sourceColumnIndex = row.split(" to ").first().takeLast(1).toInt()-1
            val destinationColumnIndex = row.split(" to ").last().take(1).toInt()-1

            for (i in 1..count) {
                val moving = state[sourceColumnIndex].takeLast(1)
                state[sourceColumnIndex] = state[sourceColumnIndex].dropLast(1)
                state[destinationColumnIndex] = state[destinationColumnIndex] + moving
            }
//            println(state.joinToString())
        }

        return state.fold("") { acc, element -> acc + element.last() }
    }

    fun part2(input: List<String>): String {
        val splitIndex = input.indexOf("")
        val initialStateStrings = input.subList(0, splitIndex)
        val instructions = input.subList(splitIndex+1, input.size)
        val state = parseInitialState(initialStateStrings)

        for (row in instructions) {
//            println(row)
            val count = row.split(" from ").first().split(" ").last().toInt()
            val sourceColumnIndex = row.split(" to ").first().takeLast(1).toInt()-1
            val destinationColumnIndex = row.split(" to ").last().take(1).toInt()-1

            val moving = state[sourceColumnIndex].takeLast(count)
            state[sourceColumnIndex] = state[sourceColumnIndex].dropLast(count)
            state[destinationColumnIndex] = state[destinationColumnIndex] + moving
//            println(state.joinToString())
        }

        return state.fold("") { acc, element -> acc + element.last() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
