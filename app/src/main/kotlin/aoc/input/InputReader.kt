package aoc.input

import java.io.File

object InputReader {
    fun read(day: Int): List<String>? {
        println("looking for the day$day.txt file")
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
}