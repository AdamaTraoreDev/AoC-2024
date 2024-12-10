package aoc.days

import aoc.common.SolverListString
import kotlin.math.pow

class Day7 : SolverListString {
    override fun part1(input: List<String>): Any {
        return calculateCalibrationResult(input)
    }

    override fun part2(input: List<String>): Any {
        return calculateCalibrationResultWithConcat(input)
    }
}

fun main() {
    val day7 = Day7()
    val input = aoc.input.InputReader.read(7)
    println("Part 1: ${input?.let { day7.part1(it) }}")
    println("Part 2: ${input?.let { day7.part2(it) }}")
}

fun calculateCalibrationResult(equations: List<String>): Long {
    return equations.sumOf { equation ->
        val (testValue, numbers) = parseEquation(equation) ?: return@sumOf 0L
        val operatorCombinations = generateOperatorCombinations(numbers.size - 1)
        if (operatorCombinations.any { evaluateExpression(numbers, it) == testValue }) testValue else 0L
    }
}

fun calculateCalibrationResultWithConcat(equations: List<String>): Long {
    return equations.sumOf { equation ->
        val (testValue, numbers) = parseEquation(equation) ?: return@sumOf 0L
        val operatorCombinations = generateOperatorCombinationsWithConcat(numbers.size - 1)
        if (operatorCombinations.any { evaluateExpression(numbers, it) == testValue }) testValue else 0L
    }
}

private fun parseEquation(equation: String): Pair<Long, List<Long>>? {
    val parts = equation.split(":")
    if (parts.size != 2) {
        println("Invalid equation format: $equation")
        return null
    }

    val testValue = parts[0].trim().toLongOrNull()
    if (testValue == null) {
        println("Invalid test value in equation: $equation")
        return null
    }

    val numbers = parts[1].trim().split(" ").mapNotNull { it.toLongOrNull() }
    if (numbers.isEmpty()) {
        println("No numbers found in equation: $equation")
        return null
    }

    return testValue to numbers
}

private fun generateOperatorCombinations(operatorCount: Int): List<List<Char>> {
    return generateCombinations(operatorCount, listOf('+', '*'))
}

private fun generateOperatorCombinationsWithConcat(operatorCount: Int): List<List<Char>> {
    return generateCombinations(operatorCount, listOf('+', '*', 'c'))
}

private fun generateCombinations(operatorCount: Int, operators: List<Char>): List<List<Char>> {
    if (operatorCount == 0) return listOf(emptyList())

    val combinations = mutableListOf<List<Char>>()

    fun backtrack(current: MutableList<Char>, depth: Int) {
        if (depth == operatorCount) {
            combinations.add(current.toList())
            return
        }

        for (op in operators) {
            current.add(op)
            backtrack(current, depth + 1)
            current.removeAt(current.size - 1)
        }
    }

    backtrack(mutableListOf(), 0)
    return combinations
}

private fun evaluateExpression(numbers: List<Long>, operators: List<Char>): Long {
    var result = numbers[0]
    for (i in operators.indices) {
        val operator = operators[i]
        val nextNumber = numbers[i + 1]
        result = when (operator) {
            '+' -> result + nextNumber
            '*' -> result * nextNumber
            'c' -> concatenate(result, nextNumber)
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }
    return result
}

private fun concatenate(a: Long, b: Long): Long {
    if (b == 0L) return a * 10 + b
    var temp = b
    var digits = 0
    while (temp > 0) {
        temp /= 10
        digits++
    }
    return a * 10.0.pow(digits.toDouble()).toLong() + b
}