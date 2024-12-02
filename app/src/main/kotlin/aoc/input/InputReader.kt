package aoc.input

import java.io.File

object InputReader {
    fun read(day: Int): List<String>? {
        println("looking for the day$day.txt file")
        return Thread.currentThread().contextClassLoader.getResourceAsStream("day$day.txt")?.reader()?.readLines()
    }

    fun readAsString(day: Int): String {
        val filename = "src/main/resources/day%02d.txt".format(day)
        return File(filename).readText().trim()
    }

    fun readAsPairList(day: Int): Pair<IntArray, IntArray> {

        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()

        read(day)?.forEach { line ->
            val (left, right) = line.split("\\s+".toRegex()).map { it.toInt() }
            leftList.add(left)
            rightList.add(right)
        }

        return leftList.toIntArray() to rightList.toIntArray()
    }
}