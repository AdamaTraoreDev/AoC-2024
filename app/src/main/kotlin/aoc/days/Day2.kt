package aoc.days

import aoc.common.Solver
import aoc.input.InputReader
import kotlin.system.measureTimeMillis

class Day2 : Solver {
    override fun part1(input: List<List<Int>>): Any {
        return input.count { isReportSafe(it) }
    }

    override fun part2(input: List<List<Int>>): Any {
        return input.count { isReportSafeWithDampener(it) }
    }


}

fun main() {
    val day2 = Day2()
    val input = InputReader.readIntegerListsFromFile(2)

    val elapsedTime = measureTimeMillis {
        println("Part 1: ${day2.part1(input)}")
    }
    val elapsedTime2 = measureTimeMillis {
        println("Part 2: ${day2.part2(input)}")
    }

    println("Elapsed time part1 : $elapsedTime ms")
    println("Elapsed time part2: $elapsedTime2 ms")
}

private fun isReportSafe(report: List<Int>): Boolean {
    // On cache la variable qui détermine la tendance du rapport (croissant ou décroissant)
    var isIncreasing: Boolean? = null

    for (i in 1 until report.size) {
        val diff = report[i] - report[i - 1]

        // Vérifier la différence entre les valeurs adjacentes (entre -3 et 3)
        if (diff !in -3..3) return false

        // Déterminer la tendance courante (croissant ou décroissant)
        if (diff > 0) {
            if (isIncreasing == false) return false // Alternance entre croissant et décroissant donc le rapport n'est pas safe
            isIncreasing = true
        } else if (diff < 0) {
            if (isIncreasing == true) return false // Alternance entre croissant et décroissant donc le rapport n'est pas safe
            isIncreasing = false
        } else {
            return false // Les valeurs adjacentes sont égales donc le rapport n'est pas safe
        }
    }

    return true
}

private fun isReportSafeWithDampener(report: List<Int>): Boolean {
    if (isReportSafe(report)) return true // Already safe

    // Check if removing any one level makes it safe
    for (i in report.indices) {
        val modifiedReport = report.filterIndexed { index, _ -> index != i }
        if (isReportSafe(modifiedReport)) return true
    }

    return false
}