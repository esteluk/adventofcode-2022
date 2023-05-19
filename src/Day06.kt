fun main() {

    fun findMarker(message: String, size: Int): Int {
        val windows = message.windowed(size, 1)
        val index = windows.indexOfFirst { it.toCharArray().distinct().size == size }
        return index + size
    }
    fun part1(input: String): Int {
        return findMarker(input, 4)
    }

    fun part1(input: List<String>): Int {
        return part1(input.first())
    }

    fun part2(input: String): Int {
        return findMarker(input, 14)
    }

    fun part2(input: List<String>): Int {
        return part2(input.first())
    }

    // test if implementation meets criteria from the description, like:
    check(part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 7)
    check(part1("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    check(part1("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    check(part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    check(part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)

    check(part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 19)
    check(part2("bvwbjplbgvbhsrlpgdmjqwftvncz") == 23)
    check(part2("nppdvjthqldpwncqszvftbrmjlhg") == 23)
    check(part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 29)
    check(part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 26)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
