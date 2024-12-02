package aoc.days

import aoc.input.InputReader
import aoc.common.Solver
import kotlin.math.abs


class Day1 : Solver {

    /**
     * Plan de résolution :
     * 1) Lier et parser les deux listes d'entrées
     * 2) Trier les deux listes en ordre croissant
     * 3) Calculer les distances pour chaque paire correspondante
     * 4) Additionner toutes les distances
     */
    override fun part1(input: Pair<IntArray, IntArray>): Any {
        return calculateDistance(input.first, input.second)
    }

    /**
     * Plan de résolution :
     * 1) Constuire une table de fréquence (map)
     * 2) Calculer les scores individuel pour chaque élément de la liste de gauche multiplier sa valeur par sa fréquence dans la liste droite
     * 3) Additionner les scores
     */
    override fun part2(input: Pair<IntArray, IntArray>): Any {
        return calculateSimilarityScoreOptimized(input.first, input.second)
    }
}

fun main() {
    val day = Day1()
    val input = InputReader.readAsPairList(1)
    println("Part 1: ${input.let { day.part1(it) }}")
    println("Part 2: ${input.let { day.part2(it) }}")
}

private fun calculateDistance(leftArray: IntArray, rightArray: IntArray): Long {
    leftArray.sort()
    rightArray.sort()

    var totalDistance = 0L
    for (i in leftArray.indices) {
        totalDistance += abs(leftArray[i] - rightArray[i])
    }

    return totalDistance
}

fun calculateSimilarityScoreOptimized(left: IntArray, right: IntArray): Long {
    // Construire une table de fréquences pour la liste droite
    val frequencyMap = mutableMapOf<Int, Int>()

    // Remplir la table de fréquences
    right.forEach { value ->
        frequencyMap[value] = frequencyMap.getOrDefault(value, 0) + 1
    }

    // Calculer les scores pour la liste gauche
    var similarityScore = 0L
    left.forEach { value ->
        similarityScore += value.toLong() * (frequencyMap[value] ?: 0)
    }

    return similarityScore
}