package aoc.days

import aoc.common.Solver
import aoc.input.InputReader

class Day2 : Solver {
    override fun part1(input: List<List<Int>>): Any {
        return "Day 2, Part 1"
    }

    override fun part2(input: List<List<Int>>): Any {
        return "Day 2, Part 2"
    }


}

fun main() {
    val day2 = Day2()
    val input = InputReader.readIntegerListsFromFile(2)
    println("Part 1: ${day2.part1(input)}")
}