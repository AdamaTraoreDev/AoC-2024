package aoc

import java.io.File

object InputReader {
    fun read(day: Int): List<String> {
        val filename = "src/main/resources/day%02d.txt".format(day)
        return File(filename).readLines()
    }

    fun readAsString(day: Int): String {
        val filename = "src/main/resources/day%02d.txt".format(day)
        return File(filename).readText().trim()
    }
}