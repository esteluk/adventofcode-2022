interface Node {
    val name: String
    fun size(): Int
}

data class File(override val name: String, val size: Int): Node {
    override fun size(): Int {
        return this.size
    }

    override fun toString(): String {
        return "- $name (file, size=$size)"
    }
}

data class Directory(override val name: String, val parent: Directory?, var children: List<Node>): Node {
    override fun size(): Int {
        return children.sumOf { it.size() }
    }

    fun addDirectory(directoryName: String): Directory {
        val existing = children.firstOrNull { it.name == directoryName }
        if (existing != null) {
            if (existing is Directory) {
                return existing
            } else {
                println("Attempting to traverse to a file")
                throw IllegalAccessException()
            }
        } else {
            val newDirectory = Directory(directoryName, this, listOf())
            this.children = children + newDirectory
            return newDirectory
        }
    }

    fun addFile(file: File) {
        val existing = children.firstOrNull { it.name == file.name }
        if (existing != null) {
            println("Attempting to add file that already exists")
            return
        }
        this.children = children + file
    }

    fun findDirectories(maxSize: Int): List<Directory> {
        val dirs = mutableListOf<Directory>()
        children.forEach {
            if (it is Directory) {
                if (it.size() < maxSize) {
                    dirs.add(it)
                }
                dirs.addAll(it.findDirectories(maxSize))
            }
        }
        return dirs
    }

    fun findLargeDirectories(minSize: Int): List<Directory> {
        val dirs = mutableListOf<Directory>()
        children.forEach {
            if (it is Directory) {
                if (it.size() >= minSize) {
                    dirs.add(it)
                }
                dirs.addAll(it.findLargeDirectories(minSize))
            }
        }
        return dirs
    }

    private fun depth(): Int {
        var depth = 0
        var p = parent
        while (p != null) {
            p = p.parent
            depth += 1
        }
        return depth
    }

    override fun toString(): String {
        return children.fold("- $name (dir, size=${size()})") { acc, element ->
            val indent = "  ".repeat(depth())
            "$acc\n$indent$element"
        }
    }
}

fun main() {

    fun parseFilesystem(input: List<String>): Directory {
        val filesystem = Directory("/", null, listOf())
        var currentDirectory: Directory = filesystem
        for (line in input) {
            if (line.startsWith("$")) {
                val command = line.substring(2)
                if (command.startsWith("cd")) {
                    val directory = command.substring(3)
                    if (directory == "/") {
                        currentDirectory = filesystem
                    } else if (directory == "..") {
                        currentDirectory = currentDirectory.parent ?: filesystem
                    } else {
                        currentDirectory = currentDirectory.addDirectory(directory)
                    }
                } else if (command.startsWith("ls")) {
                    // Do nothing, next input will parse
                }
            } else {
                // Must be file size output
                if (line.startsWith("dir")) {
                    val dirName = line.substring(4)
                    currentDirectory.addDirectory(dirName)
                } else {
                    // Is file with size
                    val split = line.split(" ")
                    val size = split[0].toInt()
                    val name = split[1]
                    val file = File(name, size)
                    currentDirectory.addFile(file)
                }
            }
        }
//        println(filesystem)
        return filesystem
    }

    fun part1(input: List<String>): Int {
        val filesystem = parseFilesystem(input)
        val smallDirectories = filesystem.findDirectories(100000)
        return smallDirectories.fold(0) { acc, element -> acc + element.size() }
    }

    fun part2(input: List<String>): Int {
        val filesystem = parseFilesystem(input)
        val totalSpace = 70000000
        val necessarySpace = 30000000
        val availableSpace = totalSpace - filesystem.size()
        val requiredSpace = necessarySpace - availableSpace
        val largeDirectories = filesystem.findLargeDirectories(requiredSpace)
        return largeDirectories.minByOrNull { it.size() }!!.size()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
