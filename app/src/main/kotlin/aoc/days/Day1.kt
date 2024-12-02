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
    override fun part1(input: List<List<Int>>): Any {
        return calculateDistance(input)
    }

    /**
     * Plan de résolution :
     * 1) Constuire une table de fréquence (map)
     * 2) Calculer les scores individuel pour chaque élément de la liste de gauche multiplier sa valeur par sa fréquence dans la liste droite
     * 3) Additionner les scores
     */
    override fun part2(input: List<List<Int>>): Any {
        return calculateSimilarityScore(input)
    }
}

fun main() {
    val day = Day1()
    val input = InputReader.readIntegerListsFromFile(1)
    println("Part 1: ${input.let { day.part1(it) }}")
    println("Part 2: ${input.let { day.part2(it) }}")
}

private fun calculateDistance(input: List<List<Int>>): Long {
    val leftArray = input.map { it[0] }.toIntArray()
    val rightArray = input.map { it[1] }.toIntArray()

    leftArray.sort()
    rightArray.sort()

    var totalDistance = 0L
    for (i in leftArray.indices) {
        totalDistance += abs(leftArray[i] - rightArray[i])
    }

    return totalDistance
}

fun calculateSimilarityScore(input: List<List<Int>>): Long {
    val leftArray = input.map { it[0] }.toIntArray()
    val rightArray = input.map { it[1] }.toIntArray()

    val frequencyMap = mutableMapOf<Int, Int>()

    rightArray.forEach { value ->
        frequencyMap[value] = frequencyMap.getOrDefault(value, 0) + 1
    }

    var similarityScore = 0L
    leftArray.forEach { value ->
        similarityScore += value.toLong() * (frequencyMap[value] ?: 0)
    }

    return similarityScore
}