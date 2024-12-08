package aoc.input

object InputReader {
    fun read(day: Int): List<String>? {
        return Thread.currentThread().contextClassLoader.getResourceAsStream("day$day.txt")?.reader()?.readLines()
    }

    fun readAsString(day: Int): String? {
        return Thread.currentThread().contextClassLoader.getResourceAsStream("day$day.txt")?.reader()?.readText()
    }

    fun readIntegerListsFromFile(day: Int): List<List<Int>> {
        val integerLists = mutableListOf<List<Int>>()
        read(day)?.forEach { line ->
            val integers = line.split("\\s+".toRegex()).map { it.toInt() }
            integerLists.add(integers)
        }
        return integerLists
    }

    fun readPairFromFile(day: Int): Pair<Set<Pair<Int, Int>>, List<List<Int>>> {
        val rules = mutableSetOf<Pair<Int, Int>>()
        val updates = mutableListOf<List<Int>>()

        Thread.currentThread().contextClassLoader.getResourceAsStream("day$day.txt")?.reader()?.readLines()
            ?.forEach { line ->
                when {
                    line.isBlank() -> return@forEach
                    '|' in line -> {
                        val (x, y) = line.split("|").map { it.toInt() }
                        rules.add(x to y)
                    }

                    else -> updates.add(line.split(",").map { it.toInt() })
                }
            }

        return rules to updates
    }
}