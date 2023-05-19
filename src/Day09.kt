import kotlin.math.absoluteValue

fun main() {

    fun printPositions(head: Pair<Int, Int>, tail: Array<Pair<Int, Int>>, size: Pair<Int, Int> = Pair(6, 5)) {
        for (i in size.second-1 downTo 0) {
            val builder = StringBuilder(size.first)
            for (j in 0 until size.first) {
                if (head.first == j && head.second == i) {
                    builder.append("H")
                } else if (tail.contains(Pair(j, i))) {
                    val index = tail.indexOf(Pair(j, i))+1
                    builder.append("$index")
                } else {
                    builder.append(".")
                }
            }
            println(builder.toString())
        }
        println()
    }
    fun part1(input: List<String>): Int {
        var headPosition = Pair(0,0)
        var tailPosition = Pair(0,0)
        val tailVisitedPositions = mutableSetOf<Pair<Int, Int>>()
        val instructions = input.map { it.split(" ") }.map { Pair(it.first(), it[1].toInt()) }

        instructions.forEach {
            val direction = it.first
//            println(it)
            for (i in 0 until it.second) {
                // Move the head
                when(direction) {
                    "U" -> headPosition = Pair(headPosition.first, headPosition.second + 1)
                    "D" -> headPosition = Pair(headPosition.first, headPosition.second - 1)
                    "L" -> headPosition = Pair(headPosition.first - 1, headPosition.second)
                    "R" -> headPosition = Pair(headPosition.first + 1, headPosition.second)
                }
//                println("Head now at ${headPosition}")
                // Move the tail
                if (tailPosition.first < headPosition.first - 1) {
                    tailPosition = Pair(tailPosition.first + 1, headPosition.second)
                } else if (tailPosition.first > headPosition.first + 1) {
                    tailPosition = Pair(tailPosition.first - 1, headPosition.second)
                }

                if (tailPosition.second < headPosition.second - 1) {
                    tailPosition = Pair(headPosition.first, tailPosition.second + 1)
                } else if (tailPosition.second > headPosition.second + 1) {
                    tailPosition = Pair(headPosition.first, tailPosition.second - 1)
                }
//                println("Tail now at ${tailPosition}")
                tailVisitedPositions.add(tailPosition)
            }
        }
        return tailVisitedPositions.size
    }

    fun part2(input: List<String>): Int {
        var headPosition = Pair(0,0)
        val knotPositions = Array(9) { Pair(0, 0) }
        val tailVisitedPositions = mutableSetOf<Pair<Int, Int>>()
        val instructions = input.map { it.split(" ") }.map { Pair(it.first(), it[1].toInt()) }

        instructions.forEach {
            val direction = it.first
//            println(it)
            for (i in 0 until it.second) {
                // Move the head
                when(direction) {
                    "U" -> headPosition = Pair(headPosition.first, headPosition.second + 1)
                    "D" -> headPosition = Pair(headPosition.first, headPosition.second - 1)
                    "L" -> headPosition = Pair(headPosition.first - 1, headPosition.second)
                    "R" -> headPosition = Pair(headPosition.first + 1, headPosition.second)
                }
//                println("Head now at ${headPosition}")
                // Move the tail
                for (j in 0 until 9) {
                    val previousPosition = if (j > 0) knotPositions[j-1] else headPosition
                    val xDiff = knotPositions[j].first - previousPosition.first
                    val yDiff = knotPositions[j].second - previousPosition.second

                    val isNotTouching = xDiff.absoluteValue > 1 || yDiff.absoluteValue > 1
                    val isSameAxis = xDiff == 0 || yDiff == 0
                    if (isNotTouching && !isSameAxis) {
                        val newX = if (xDiff < 0) knotPositions[j].first + 1 else knotPositions[j].first - 1
                        val newY = if (yDiff < 0) knotPositions[j].second + 1 else knotPositions[j].second - 1

                        knotPositions[j] = Pair(newX, newY)
                    } else if (isNotTouching) {
                        val newX = if (xDiff < -1) knotPositions[j].first + 1 else if (xDiff > 1) knotPositions[j].first - 1 else knotPositions[j].first
                        val newY = if (yDiff < -1) knotPositions[j].second + 1 else if (yDiff > 1) knotPositions[j].second - 1 else knotPositions[j].second

                        knotPositions[j] = Pair(newX, newY)
                    }

                }

//                printPositions(headPosition, knotPositions)
                tailVisitedPositions.add(knotPositions[8])
            }

//            for (j in 0 until 9) {
//                println("Knot ${j+1} at ${knotPositions[j]}")
//            }
        }
        return tailVisitedPositions.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
//    check(part1(testInput) == 13)
    check(part2(testInput) == 1)

    val test2Input = readInput("Day09_test2")
    check(part2(test2Input) == 36)

    val input = readInput("Day09")
//    println(part1(input))
    println(part2(input))
}
