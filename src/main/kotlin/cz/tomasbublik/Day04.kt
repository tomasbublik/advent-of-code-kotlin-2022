package cz.tomasbublik

fun main() {
    fun createPairsStructure(input: List<String>): ArrayList<Pair<IntRange, IntRange>> {
        val duals = ArrayList<Pair<IntRange, IntRange>>()
        for (line in input) {
            val firstRange = line.split(",")[0].split("-")[0].toInt()..line.split(",")[0].split("-")[1].toInt()
            val secondRange = line.split(",")[1].split("-")[0].toInt()..line.split(",")[1].split("-")[1].toInt()
            val currentPair = Pair<IntRange, IntRange>(firstRange, secondRange)
            duals.add(currentPair)
        }
        return duals
    }

    fun part1(input: List<String>): Int {
        var numberOfCovers = 0
        val duals = createPairsStructure(input)

        // test is one contained in other
        for (pair in duals) {
            if (pair.first == pair.second) {
                numberOfCovers++
                continue
            }
            // the first one is contained in the second one
            if (pair.second.contains(pair.first.first) && pair.second.contains(pair.first.last)) {
                numberOfCovers++
            }
            // the second one is contained in the first one
            if (pair.first.contains(pair.second.first) && pair.first.contains(pair.second.last)) {
                numberOfCovers++
            }
        }
        return numberOfCovers
    }

    fun part2(input: List<String>): Int {
        val duals = createPairsStructure(input)
        var numberOfCovers = 0

        for (pair in duals) {
            if (pair.second.contains(pair.first.first) || pair.second.contains(pair.first.last)) {
                numberOfCovers++
                continue
            }

            if (pair.first.contains(pair.second.first) || pair.first.contains(pair.second.last)) {
                numberOfCovers++
                continue
            }
        }

        return numberOfCovers
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readFileAsLinesUsingUseLines("src/main/resources/day_4_input_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readFileAsLinesUsingUseLines("src/main/resources/day_4_input")
    println(part1(input))
    println(part2(input))
}
