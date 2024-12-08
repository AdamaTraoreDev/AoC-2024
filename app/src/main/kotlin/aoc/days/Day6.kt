package aoc.days

import aoc.common.SolverListString
import aoc.input.InputReader

data class State(val x: Int, val y: Int, val direction: Int)

class Day6 : SolverListString {
    override fun part1(input: List<String>): Any {
        return simulateGuard(input)
    }

    override fun part2(input: List<String>): Any {
        return countLoopPositions(input)
    }
}

fun main() {
    val day6 = Day6()
    val input = InputReader.read(6)
    println("Part 1: ${input?.let { day6.part1(it) }}")
    println("Part 2: ${input?.let { day6.part2(it) }}")
}

fun parseMap(input: List<String>): Triple<Int, Int, Char> {
    for (row in input.indices) {
        for (col in input[row].indices) {
            val cell = input[row][col]
            if (cell in "^v<>") {
                return Triple(row, col, cell) // Starting position and direction
            }
        }
    }
    throw IllegalArgumentException("Guard's starting position not found")
}

fun simulateGuard(mapInput: List<String>): Int {
    // Dimensions de la carte
    val height = mapInput.size
    if (height == 0) return 0
    val width = mapInput[0].length

    // Convertir la carte en grille
    val grid = mapInput.map { it.toCharArray() }

    // Directions dans l'ordre : Haut, Droite, Bas, Gauche
    val directions = listOf(
        Pair(0, -1),  // Haut
        Pair(1, 0),   // Droite
        Pair(0, 1),   // Bas
        Pair(-1, 0)   // Gauche
    )

    // Trouver la position initiale du garde et sa direction
    var guardX = -1
    var guardY = -1
    var directionIndex = -1 // 0: Haut, 1: Droite, 2: Bas, 3: Gauche

    outer@ for (y in 0 until height) {
        for (x in 0 until width) {
            when (grid[y][x]) {
                '^' -> {
                    guardX = x
                    guardY = y
                    directionIndex = 0
                    break@outer
                }
                '>' -> {
                    guardX = x
                    guardY = y
                    directionIndex = 1
                    break@outer
                }
                'v' -> {
                    guardX = x
                    guardY = y
                    directionIndex = 2
                    break@outer
                }
                '<' -> {
                    guardX = x
                    guardY = y
                    directionIndex = 3
                    break@outer
                }
            }
        }
    }

    // Vérifier si le garde a été trouvé
    if (guardX == -1 || guardY == -1 || directionIndex == -1) {
        println("Erreur : Position initiale du garde non trouvée ou multiple.")
        return 0
    }

    // Ensemble pour stocker les positions visitées
    val visited = mutableSetOf<Pair<Int, Int>>()
    visited.add(Pair(guardX, guardY))

    var currentX = guardX
    var currentY = guardY
    var currentDirection = directionIndex

    // Compteur pour détecter les cycles (4 tours consécutifs sans avancer)
    var turnCounter = 0
    val maxTurnsWithoutMove = 4

    // Optionnel : Limiter le nombre total d'itérations pour éviter les boucles infinies
    var totalIterations = 0
    val maxTotalIterations = 1000000 // Par exemple, 1 million d'itérations

    while (true) {
        // Calculer la position devant le garde
        val (dx, dy) = directions[currentDirection]
        val nextX = currentX + dx
        val nextY = currentY + dy

        // Vérifier si la position devant est un obstacle
        val hasObstacle = if (nextX in 0 until width && nextY in 0 until height) {
            grid[nextY][nextX] == '#'
        } else {
            false // Considérer hors de la carte comme une sortie, pas un obstacle
        }

        if (hasObstacle) {
            // Tourner à droite
            currentDirection = (currentDirection + 1) % 4
            turnCounter += 1

            if (turnCounter >= maxTurnsWithoutMove) {
                println("Le garde est bloqué après $totalIterations itérations.")
                break
            }
        } else {
            // Avancer d'une case
            currentX = nextX
            currentY = nextY

            // Réinitialiser le compteur de tours consécutifs
            turnCounter = 0

            // Vérifier si le garde est sorti de la carte après avoir avancé
            if (currentX !in 0 until width || currentY !in 0 until height) {
                break
            }

            // Ajouter la position visitée
            visited.add(Pair(currentX, currentY))
        }

        // Incrémenter le compteur total d'itérations
        totalIterations += 1

        // Vérifier si le nombre total d'itérations dépasse la limite
        if (totalIterations >= maxTotalIterations) {
            println("Nombre maximum d'itérations atteint. Arrêt de la simulation.")
            break
        }
    }

    return visited.size
}

fun countLoopPositions(mapInput: List<String>): Int {
    // Dimensions de la carte
    val height = mapInput.size
    if (height == 0) return 0
    val width = mapInput[0].length

    // Convertir la carte en grille
    val grid = mapInput.map { it.toCharArray() }.toMutableList()

    // Directions dans l'ordre : Haut, Droite, Bas, Gauche
    val directions = listOf(
        Pair(0, -1),  // Haut
        Pair(1, 0),   // Droite
        Pair(0, 1),   // Bas
        Pair(-1, 0)   // Gauche
    )

    // Trouver la position initiale du garde et sa direction
    var guardX = -1
    var guardY = -1
    var directionIndex = -1 // 0: Haut, 1: Droite, 2: Bas, 3: Gauche

    outer@ for (y in 0 until height) {
        for (x in 0 until width) {
            when (grid[y][x]) {
                '^' -> {
                    guardX = x
                    guardY = y
                    directionIndex = 0
                    break@outer
                }
                '>' -> {
                    guardX = x
                    guardY = y
                    directionIndex = 1
                    break@outer
                }
                'v' -> {
                    guardX = x
                    guardY = y
                    directionIndex = 2
                    break@outer
                }
                '<' -> {
                    guardX = x
                    guardY = y
                    directionIndex = 3
                    break@outer
                }
            }
        }
    }

    // Vérifier si le garde a été trouvé
    if (guardX == -1 || guardY == -1 || directionIndex == -1) {
        println("Erreur : Position initiale du garde non trouvée ou multiple.")
        return 0
    }

    var loopCount = 0

    // Itérer sur toutes les positions possibles pour ajouter une obstruction
    for (y in 0 until height) {
        for (x in 0 until width) {
            // Ignorer les cellules déjà occupées par des obstacles ou la position initiale du garde
            if (grid[y][x] == '#' || (x == guardX && y == guardY)) {
                continue
            }

            // Placer une obstruction temporaire
            grid[y][x] = '#'

            // Simuler le déplacement du garde
            if (doesGuardLoop(grid, width, height, guardX, guardY, directionIndex, directions)) {
                loopCount++
            }

            // Restaurer la cellule
            grid[y][x] = '.'
        }
    }

    return loopCount
}

fun doesGuardLoop(
    grid: List<CharArray>,
    width: Int,
    height: Int,
    startX: Int,
    startY: Int,
    startDir: Int,
    directions: List<Pair<Int, Int>>
): Boolean {
    var currentX = startX
    var currentY = startY
    var currentDirection = startDir

    // Ensemble pour stocker les états visités
    val visitedStates = mutableSetOf<String>()

    while (true) {
        val stateKey = "$currentX,$currentY,$currentDirection"
        if (stateKey in visitedStates) {
            // Cycle détecté
            return true
        }
        visitedStates.add(stateKey)

        // Calculer la position devant le garde
        val (dx, dy) = directions[currentDirection]
        val nextX = currentX + dx
        val nextY = currentY + dy

        // Vérifier si le garde quitte la carte
        if (nextX !in 0 until width || nextY !in 0 until height) {
            return false
        }

        // Vérifier si la position devant est un obstacle
        if (grid[nextY][nextX] == '#') {
            // Tourner à droite
            currentDirection = (currentDirection + 1) % 4
        } else {
            // Avancer d'une case
            currentX = nextX
            currentY = nextY
        }
    }
}