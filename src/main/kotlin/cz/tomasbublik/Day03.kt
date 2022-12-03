package cz.tomasbublik

fun main() {
    fun getSameChar(firstHalf: CharSequence, secondHalf: CharSequence): Char {
        for (firstChar in firstHalf) {
            for (secondChar in secondHalf) {
                if (firstChar == secondChar) {
                    return firstChar
                }
            }
        }
        throw IllegalStateException("Every line must have same chars!")
    }

    fun getSameChar(first: CharSequence, second: CharSequence, third: CharSequence): Char {
        for (firstChar in first) {
            for (secondChar in second) {
                if (firstChar == secondChar) {
                    for (thirdChar in third) {
                        if (thirdChar == firstChar) {
                            return firstChar
                        }
                    }
                }
            }
        }
        throw IllegalStateException("Every line must have same chars!")
    }

    fun getPriorityForLetter(letter: Char): Int {
        var c = 'a'
        for (prio1 in 1..26) {
            if (letter == c) {
                return prio1
            }
            ++c
        }

        c = 'A'
        for (prio2 in 27..52) {
            if (letter == c) {
                return prio2
            }
            ++c
        }
        throw IllegalStateException("Every possible letter must have a priority!")
    }

    fun part1(input: List<String>): Int {
        val sameLettersList = ArrayList<Char>()

        for (line in input) {
            val firstHalf = line.subSequence(0, line.length / 2)
            val secondHalf = line.subSequence(line.length / 2, line.length)
            sameLettersList.add(getSameChar(firstHalf, secondHalf))

            // println("half 1: $firstHalf, half 2: $secondHalf")
        }

        return sameLettersList.sumOf { getPriorityForLetter(it) }
    }

    fun part2(input: List<String>): Int {
        val sameLettersList = ArrayList<Char>()
        var tempInput = input
        for (group in 1..(input.size / 3)) {
            val currentGroup = tempInput.take(3)
            tempInput = tempInput.drop(3)
            sameLettersList.add(getSameChar(currentGroup[0], currentGroup[1], currentGroup[2]))
            // println(currentGroup)
        }
        return sameLettersList.sumOf { getPriorityForLetter(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readFileAsLinesUsingUseLines("src/main/resources/day_3_input_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readFileAsLinesUsingUseLines("src/main/resources/day_3_input")
    println(part1(input))
    println(part2(input))
}
