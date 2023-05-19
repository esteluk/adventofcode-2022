fun main() {
    fun parseHeights(input: List<String>): Array<IntArray> {
        val width = input.first().length
        val array = Array(input.size) { IntArray(width) }
        for (i in 0..input.lastIndex) {
            for (j in 0 until width) {
                array[i][j] = input[i][j].toString().toInt()
            }
        }
        return array
    }

    fun part1(input: List<String>): Int {
        val width = input.first().length
        val array = parseHeights(input)

        var interiorTreeCount = (width+input.size)*2 - 4
        for (i in 1 until input.lastIndex) {
            for (j in 1 until width-1) {
                val checkValue = array[i][j]
                val topVisible = (0 until i).map { array[it][j] }.any { it >= checkValue }.not()
                val leftVisible = (0 until j).map { array[i][it] }.any { it >= checkValue }.not()
                val bottomVisible = (i+1..input.lastIndex).map { array[it][j] }.any { it >= checkValue }.not()
                val rightVisible = (j+1 until width).map { array[i][it] }.any { it >= checkValue }.not()

                // If any value >= checkValue, return false
                if (topVisible || leftVisible || bottomVisible || rightVisible) {
                    interiorTreeCount += 1
                }
            }
        }
        return interiorTreeCount
    }

    fun part2(input: List<String>): Int {
        val width = input.first().length
        val array = parseHeights(input)

        var bestScore = 0
        for (i in 1 until input.lastIndex) {
            for (j in 1 until width-1) {
                val checkValue = array[i][j]
                val topCount = ((i - 1 downTo 0).map { array[it][j] }.takeWhile { it < checkValue }
                    .count() + 1).coerceAtMost((i - 1 downTo 0).count())
                val leftCount = ((j-1 downTo 0).map { array[i][it] }.takeWhile { it < checkValue }.count() + 1)
                    .coerceAtMost((j-1 downTo 0).count())
                val bottomCount = ((i+1 .. input.lastIndex).map { array[it][j] }.takeWhile { it < checkValue }.count() + 1)
                    .coerceAtMost((i+1 .. input.lastIndex).count())
                val rightCount = ((j+1 until width).map { array[i][it] }.takeWhile { it < checkValue }.count() + 1).coerceAtMost((j+1 until width).count())

                val score = topCount * leftCount * bottomCount * rightCount
                if (score > bestScore) {
                    bestScore = score
                }
            }
        }
        return bestScore
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
