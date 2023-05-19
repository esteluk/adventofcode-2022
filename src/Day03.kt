fun main() {

    fun code(char: Char): Int {
        return if (char.isLowerCase()) {
            char.code - 96
        } else {
            char.code - 38
        }
    }

    fun part1(input: List<String>): Int {
        val chars = input.map {
            val length = it.length
            val co1 = it.substring(0, length/2).toCharArray().toSet()
            val co2 = it.substring(length/2).toCharArray().asIterable().toSet()

            co1.intersect(co2).first()
        }.map { code(it) }
        return chars.sum()
    }


    fun part2(input: List<String>): Int {
        return input.chunked(3).map { chunk ->
            chunk.map { it.toCharArray().toSet() }
                .reduce { intersection, set -> intersection.intersect(set) }
                .map { code(it) }
                .first()
        }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
