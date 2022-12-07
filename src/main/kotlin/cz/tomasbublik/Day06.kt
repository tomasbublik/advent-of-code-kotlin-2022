package cz.tomasbublik

import java.util.LinkedList
import java.util.Queue

fun String.allUnique(): Boolean = all(hashSetOf<Char>()::add)
fun Queue<Char>.allUnique(): Boolean = all(hashSetOf<Char>()::add)
fun main() {
    fun allCharsDifferent(nums: Queue<Char>): Boolean {
        return nums.allUnique()
    }

    fun calculateArrayOfMarkers(
        input: List<String>,
        markerlength: Int
    ): ArrayList<Int> {
        val arrayOfMarkers = ArrayList<Int>()
        for (line in input) {
            val nums: Queue<Char> = LinkedList<Char>()
            for (charIndex in line.indices) {
                if (nums.size == markerlength) {
                    nums.remove()
                }
                nums.add(line[charIndex])
                if (nums.size == markerlength) {
                    if (allCharsDifferent(nums)) {
                        arrayOfMarkers.add(charIndex + 1)
                        break
                    }
                }
            }
        }
        return arrayOfMarkers
    }

    fun part1(input: List<String>): List<Int> {
        val markerLength = 4

        return calculateArrayOfMarkers(input, markerLength)
    }

    fun part2(input: List<String>): List<Int> {
        val markerLength = 14

        val arrayOfMarkers = calculateArrayOfMarkers(input, markerLength)

        return arrayOfMarkers
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readFileAsLinesUsingUseLines("src/main/resources/day_6_input_test")
    check(part1(testInput) == listOf(7, 5, 6, 10, 11))
    check(part2(testInput) == listOf(19, 23, 23, 29, 26))

    val input = readFileAsLinesUsingUseLines("src/main/resources/day_6_input")
    println(part1(input))
    println(part2(input))
}
