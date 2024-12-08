package aoc.days

import aoc.common.SolverListString
import aoc.input.InputReader

class Day4 : SolverListString {
    override fun part1(input: List<String>): Any {
        return countXmasOccurrences(input)
    }

    override fun part2(input: List<String>): Any {
        return countXMasPatterns(input)
    }
}

fun main() {
    val day4 = Day4()
    val input = InputReader.read(4)
    println("Part 1: ${input?.let { day4.part1(it) }}")
    println("Part 2: ${input?.let { day4.part2(it) }}")
}

private fun countXmasOccurrences(grid: List<String>, word: String = "XMAS"): Int {
    val directions = listOf(
        Pair(0, 1),  // Horizontal right
        Pair(0, -1), // Horizontal left
        Pair(1, 0),  // Vertical down
        Pair(-1, 0), // Vertical up
        Pair(1, 1),  // Diagonal down-right
        Pair(1, -1), // Diagonal down-left
        Pair(-1, 1), // Diagonal up-right
        Pair(-1, -1) // Diagonal up-left
    )

    val numRows = grid.size
    val numCols = grid[0].length
    var count = 0

    // Check every starting point in the grid
    for (row in grid.indices) {
        for (col in grid[row].indices) {
            // Try all directions
            for ((dx, dy) in directions) {
                var matches = true

                // Check the entire word
                for (i in word.indices) {
                    val newRow = row + i * dx
                    val newCol = col + i * dy

                    // Check if the new row and column are within the grid bounds
                    if (newRow !in 0 until numRows || newCol !in 0 until numCols) {
                        matches = false
                        break
                    }

                    // Check if the character in the grid matches the corresponding character in the word
                    if (grid[newRow][newCol] != word[i]) {
                        matches = false
                        break
                    }
                }

                if (matches) count++
            }
        }
    }

    return count
}

private fun countXMasPatterns(grid: List<String>): Int {
    val rows = grid.size
    val cols = grid[0].length
    val validMAS = setOf("MAS", "SAM") // Allow forward and backward MAS
    var count = 0

    // Iterate through the grid, treating each cell as the potential center
    for (row in 1 until rows - 1) {
        for (col in 1 until cols - 1) {
            if (grid[row][col] == 'A') {
                // Check top-left to bottom-right diagonal
                val topLeftDiag = "${grid[row - 1][col - 1]}${grid[row][col]}${grid[row + 1][col + 1]}"
                // Check top-right to bottom-left diagonal
                val topRightDiag = "${grid[row - 1][col + 1]}${grid[row][col]}${grid[row + 1][col - 1]}"

                // Check if both diagonals form valid MAS patterns
                if (topLeftDiag in validMAS && topRightDiag in validMAS) {
                    count++
                }
            }
        }
    }

    return count
}