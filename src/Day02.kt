fun main() {

    val score = mapOf(
        "A X" to 4,
        "A Y" to 8,
        "A Z" to 3,
        "B X" to 1,
        "B Y" to 5,
        "B Z" to 9,
        "C X" to 7,
        "C Y" to 2,
        "C Z" to 6,
    )

    fun part1(input: List<String>): Int {
        return input.sumOf { score[it] ?: 0 }
    }

    // X - lose
    // Y - draw
    // Z - win

    val newScore = mapOf(
        "A X" to 3,
        "A Y" to 4,
        "A Z" to 8,
        "B X" to 1,
        "B Y" to 5,
        "B Z" to 9,
        "C X" to 2,
        "C Y" to 6,
        "C Z" to 7,
    )

    fun part2(input: List<String>): Int {
        return input.sumOf { newScore[it] ?: 0 }
    }

    // Rock      A X    1
    // Paper     B Y    2
    // Scissors  C Z    3

    // 0 3 6


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
