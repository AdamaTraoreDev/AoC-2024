package aoc.days

import aoc.common.SolverListString
import aoc.input.InputReader

class Day10 : SolverListString {
    override fun part1(input: List<String>): Any {
        return sumOfAllTrailhead(input)
    }

    override fun part2(input: List<String>): Any {
        return sumOfAllDistinctTrailhead(input)
    }
}

fun main() {
    val day10 = Day10()
    val input = InputReader.read(10)
    println("Part 1: ${input?.let { day10.part1(it) }}")
    println("Part 2: ${input?.let { day10.part2(it) }}")
}

private fun sumOfAllTrailhead(input: List<String>): Int {
    val grid = input.map { it.map { char -> char.digitToInt() }.toTypedArray() }.toTypedArray()
    val rows = grid.size
    val cols = grid[0].size

    // Trouver toutes les positions de trailheads (hauteur 0)
    val trailheads = mutableListOf<Pair<Int, Int>>()
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (grid[i][j] == 0) {
                trailheads.add(Pair(i, j))
            }
        }
    }

    var totalScore = 0
    for (trailhead in trailheads) {
        val score = findReachableNines(grid, trailhead.first, trailhead.second, rows, cols)
        totalScore += score
    }
    return totalScore
}

private fun sumOfAllDistinctTrailhead(input: List<String>): Int {
    val grid = input.map { it.map { char -> char.digitToInt() }.toTypedArray() }.toTypedArray()
    val rows = grid.size
    val cols = grid[0].size

    // Trouver toutes les positions de trailheads (hauteur 0)
    val trailheads = mutableListOf<Pair<Int, Int>>()
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (grid[i][j] == 0) {
                trailheads.add(Pair(i, j))
            }
        }
    }

    var totalRating = 0
    for (trailhead in trailheads) {
        val rating = countDistinctTrails(grid, trailhead.first, trailhead.second, rows, cols)
        totalRating += rating
    }
    return totalRating
}

// Fonction pour trouver les 9 atteignables depuis un trailhead
fun findReachableNines(
    grid: Array<Array<Int>>,
    startRow: Int,
    startCol: Int,
    rows: Int,
    cols: Int
): Int {
    // Utiliser un ensemble pour stocker les positions de 9 atteintes
    val reachableNines = mutableSetOf<Pair<Int, Int>>()
    // Utiliser une matrice pour marquer les positions visitées
    val visited = Array(rows) { BooleanArray(cols) }

    // Fonction récursive pour explorer les chemins
    fun dfs(row: Int, col: Int, currentHeight: Int) {
        // Marquer la position actuelle comme visitée
        visited[row][col] = true

        // Définir les directions possibles : haut, bas, gauche, droite
        val directions = listOf(
            Pair(-1, 0), // haut
            Pair(1, 0),  // bas
            Pair(0, -1), // gauche
            Pair(0, 1)   // droite
        )

        for ((dr, dc) in directions) {
            val newRow = row + dr
            val newCol = col + dc

            // Vérifier les limites de la grille
            if (newRow in 0 until rows && newCol in 0 until cols) {
                val nextHeight = grid[newRow][newCol]
                // Vérifier si la hauteur augmente de exactement 1 et si non visité
                if (nextHeight == currentHeight + 1 && !visited[newRow][newCol]) {
                    if (nextHeight == 9) {
                        reachableNines.add(Pair(newRow, newCol))
                    } else {
                        dfs(newRow, newCol, nextHeight)
                    }
                }
            }
        }

        // Démarquer la position actuelle pour d'autres chemins
        visited[row][col] = false
    }

    // Commencer la recherche depuis le trailhead
    dfs(startRow, startCol, grid[startRow][startCol])

    return reachableNines.size
}

fun countDistinctTrails(
    grid: Array<Array<Int>>,
    startRow: Int,
    startCol: Int,
    rows: Int,
    cols: Int
): Int {
    var trailCount = 0
    // Utiliser une matrice pour marquer les positions visitées dans le chemin actuel
    val visited = Array(rows) { BooleanArray(cols) }

    // Fonction récursive pour explorer les chemins
    fun dfs(row: Int, col: Int, currentHeight: Int) {
        // Marquer la position actuelle comme visitée
        visited[row][col] = true

        // Définir les directions possibles : haut, bas, gauche, droite
        val directions = listOf(
            Pair(-1, 0), // haut
            Pair(1, 0),  // bas
            Pair(0, -1), // gauche
            Pair(0, 1)   // droite
        )

        for ((dr, dc) in directions) {
            val newRow = row + dr
            val newCol = col + dc

            // Vérifier les limites de la grille
            if (newRow in 0 until rows && newCol in 0 until cols) {
                val nextHeight = grid[newRow][newCol]
                // Vérifier si la hauteur augmente de exactement 1 et si non visité
                if (nextHeight == currentHeight + 1 && !visited[newRow][newCol]) {
                    if (nextHeight == 9) {
                        trailCount += 1
                    } else {
                        dfs(newRow, newCol, nextHeight)
                    }
                }
            }
        }

        // Démarquer la position actuelle pour d'autres chemins
        visited[row][col] = false
    }

    // Commencer la recherche depuis le trailhead
    dfs(startRow, startCol, grid[startRow][startCol])

    return trailCount
}