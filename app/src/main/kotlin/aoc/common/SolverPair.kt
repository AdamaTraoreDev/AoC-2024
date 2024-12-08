package aoc.common

interface SolverPair {
    fun part1(input: Pair<Set<Pair<Int, Int>>, List<List<Int>>>): Any
    fun part2(input: Pair<Set<Pair<Int, Int>>, List<List<Int>>>): Any
}