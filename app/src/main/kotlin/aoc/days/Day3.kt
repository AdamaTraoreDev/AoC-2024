package aoc.days

import aoc.common.Solver
import aoc.common.SolverString
import aoc.input.InputReader
import java.util.regex.Pattern

class Day3 : SolverString {
    override fun part1(input: String): Any {
        return calculateSumOfMultiplications(input)
    }

    override fun part2(input: String): Any {
        return calculateSumWithConditions(input)
    }

}

fun main() {
    val day3 = Day3()
    val input = InputReader.readAsString(3)
    println("Part 1: ${input?.let { day3.part1(it) }}")
    println("Part 2: ${input?.let { day3.part2(it) }}")
}

private fun calculateSumOfMultiplications(memory: String): Int {

    val pattern = Pattern.compile("""mul\((\d{1,3}),(\d{1,3})\)""")
    val matcher = pattern.matcher(memory)

    var totalSum = 0

    // Find all matches
    while (matcher.find()) {
        // Extract X and Y from the matched groups
        val x = matcher.group(1).toInt()
        val y = matcher.group(2).toInt()

        // Compute the product and add to the total sum
        totalSum += x * y
    }

    return totalSum
}

private fun calculateSumWithConditions(memory: String): Int {
    val mulPattern = Pattern.compile("""mul\((\d{1,3}),(\d{1,3})\)""")
    val doPattern = Pattern.compile("""do\(\)""")
    val dontPattern = Pattern.compile("""don't\(\)""")

    var isEnabled = true // Start with mul instructions enabled
    var totalSum = 0

    val matcher = Pattern.compile("""mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""").matcher(memory)

    while (matcher.find()) {
        val match = matcher.group()

        // Check for do() and don't()
        if (doPattern.matcher(match).matches()) {
            isEnabled = true
        } else if (dontPattern.matcher(match).matches()) {
            isEnabled = false
        } else {
            // Process mul(X,Y) only if enabled
            if (isEnabled && mulPattern.matcher(match).matches()) {
                val mulMatcher = mulPattern.matcher(match)
                if (mulMatcher.matches()) {
                    val x = mulMatcher.group(1).toInt()
                    val y = mulMatcher.group(2).toInt()
                    totalSum += x * y
                }
            }
        }
    }

    return totalSum
}