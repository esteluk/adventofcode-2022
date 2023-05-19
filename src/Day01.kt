fun main() {
    fun part1(input: List<String>): Int {
        var i = 0
        val arr = IntArray(input.size)
        input.forEach {
            if (it.isEmpty()) {
                i += 1
            } else {
                arr[i] += it.toInt()
            }
        }
        return arr.max()
    }

    fun part2(input: List<String>): Int {
        var i = 0
        val arr = IntArray(input.size)
        input.forEach {
            if (it.isEmpty()) {
                i += 1
            } else {
                arr[i] += it.toInt()
            }
        }
        arr.sortDescending()
        return arr[0]+arr[1]+arr[2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
