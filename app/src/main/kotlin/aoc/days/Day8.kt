package aoc.days

import aoc.common.SolverListString
import aoc.input.InputReader

class Day8 : SolverListString {
    override fun part1(input: List<String>): Any {
        return calculateAntinodes(input)
    }

    override fun part2(input: List<String>): Any {
        return 0
    }
}

fun main() {
    val day8 = Day8()
    val input = InputReader.read(8)
    println("Part 1: ${input?.let { day8.part1(it) }}")
    println("Part 2: ${input?.let { day8.part2(it) }}")
}

fun calculateAntinodes(map: List<String>): Int {
    val height = map.size
    if (height == 0) return 0
    val width = map[0].length

    // Map pour grouper les antennes par fréquence
    val frequencyMap = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()

    // Parcours de la carte pour identifier les antennes
    for (y in 0 until height) {
        for (x in 0 until width) {
            val cell = map[y][x]
            if (cell != '.') {
                frequencyMap.computeIfAbsent(cell) { mutableListOf() }.add(Pair(x, y))
            }
        }
    }

    // Ensemble pour stocker les positions d'antinodes uniques
    val antinodeSet = mutableSetOf<Pair<Int, Int>>()

    // Pour chaque fréquence, traiter les antennes correspondantes
    for ((frequency, antennas) in frequencyMap) {
        if (antennas.size < 2) continue // Au moins deux antennes nécessaires pour former des antinodes

        // Parcours de toutes les paires uniques d'antennes
        for (i in 0 until antennas.size - 1) {
            for (j in i + 1 until antennas.size) {
                val antennaA = antennas[i]
                val antennaB = antennas[j]

                // Calcul des positions d'antinodes P1 et P2
                val p1 = calculateAntinodePosition(antennaA, antennaB)
                val p2 = calculateAntinodePosition(antennaB, antennaA)

                // Vérification des limites de la carte et ajout aux antinodes si valide
                if (isWithinBounds(p1, width, height)) {
                    antinodeSet.add(p1)
                }
                if (isWithinBounds(p2, width, height)) {
                    antinodeSet.add(p2)
                }
            }
        }
    }

    return antinodeSet.size
}

/**
 * Calcule la position d'un antinode en fonction de deux antennes.
 *
 * @param from L'antenne de référence.
 * @param to L'autre antenne.
 * @return La position de l'antinode calculée.
 */
fun calculateAntinodePosition(from: Pair<Int, Int>, to: Pair<Int, Int>): Pair<Int, Int> {
    val (x1, y1) = from
    val (x2, y2) = to

    // Correction de la formule :
    // P1 = 2*A - B
    val antinodeX = 2 * x1 - x2
    val antinodeY = 2 * y1 - y2

    return Pair(antinodeX, antinodeY)
}

/**
 * Vérifie si une position est à l'intérieur des limites de la carte.
 *
 * @param position La position à vérifier.
 * @param width La largeur de la carte.
 * @param height La hauteur de la carte.
 * @return Vrai si la position est dans les limites, sinon Faux.
 */
fun isWithinBounds(position: Pair<Int, Int>, width: Int, height: Int): Boolean {
    val (x, y) = position
    return x in 0 until width && y in 0 until height
}