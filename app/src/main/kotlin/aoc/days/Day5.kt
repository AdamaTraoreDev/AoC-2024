package aoc.days

import aoc.common.SolverPair
import aoc.input.InputReader

class Day5 : SolverPair {
    override fun part1(input: Pair<Set<Pair<Int, Int>>, List<List<Int>>>): Any {
        // Parse the input file
        val (rules, updates) = input

        // Validate updates and find middle pages
        val middlePages = updates.filter { update ->
            isUpdateValid(update, rules)
        }.map { update ->
            findMiddlePage(update)
        }

        // Sum up the middle pages
        return middlePages.sum()
    }

    override fun part2(input: Pair<Set<Pair<Int, Int>>, List<List<Int>>>): Any {
        // Parse the input file
        val (rules, updates) = input

        // Identify invalid updates and fix their order
        val fixedMiddlePages = updates.filterNot { update ->
            isUpdateValid(update, rules)
        }.map { invalidUpdate ->
            val fixedUpdate = topologicalSort(invalidUpdate, rules)
            findMiddlePage(fixedUpdate)
        }

        // Sum up the middle pages of fixed updates
        return fixedMiddlePages.sum()
    }

}
fun main() {
    val day5 = Day5()
    val input = InputReader.readPairFromFile(5)
    println("Part 1: ${input.let { day5.part1(it) }}")
    println("Part 2: ${input.let { day5.part2(it) }}")
}

private fun isUpdateValid(update: List<Int>, rules: Set<Pair<Int, Int>>): Boolean {
    val pagePositions = update.withIndex().associate { it.value to it.index }
    return rules.all { (x, y) ->
        if (x in pagePositions && y in pagePositions) {
            pagePositions[x]!! < pagePositions[y]!!
        } else true
    }
}

private fun findMiddlePage(update: List<Int>): Int {
    val midIndex = update.size / 2
    return update[midIndex]
}


private fun topologicalSort(update: List<Int>, rules: Set<Pair<Int, Int>>): List<Int> {
    val graph = mutableMapOf<Int, MutableSet<Int>>()
    val inDegree = mutableMapOf<Int, Int>()

    // Build the graph and in-degree count
    for ((x, y) in rules) {
        if (x in update && y in update) {
            graph.computeIfAbsent(x) { mutableSetOf() }.add(y)
            inDegree[y] = inDegree.getOrDefault(y, 0) + 1
            inDegree.putIfAbsent(x, 0)
        }
    }

    // Initialize the queue with nodes having zero in-degree
    val queue = ArrayDeque(update.filter { inDegree.getOrDefault(it, 0) == 0 })
    val result = mutableListOf<Int>()

    // Perform topological sort
    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()
        result.add(node)

        for (neighbor in graph[node] ?: emptySet()) {
            inDegree[neighbor] = inDegree[neighbor]!! - 1
            if (inDegree[neighbor] == 0) {
                queue.addLast(neighbor)
            }
        }
    }

    return result
}