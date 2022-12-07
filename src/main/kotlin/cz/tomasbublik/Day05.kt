package cz.tomasbublik

fun main() {
    fun createStacks(input: List<String>): ArrayList<ArrayDeque<Char>> {
        val cratesLines = input.slice(0 until input.indexOf(""))
        val listOfStacks = ArrayList<ArrayDeque<Char>>()
        val numberOfStacks = cratesLines.last().split("   ").last().toInt()
        for (i in 1..numberOfStacks) {
            val stack = ArrayDeque<Char>()
            listOfStacks.add(stack)
        }
        for (cratesLine in cratesLines.reversed()) {
            // is not the last one read on by one at indexes: 1, 5, 9, 13, 17
            if (cratesLines.indexOf(cratesLine) != cratesLines.size - 1) {
                for (i in 1..numberOfStacks) {
                    if (cratesLine.length > ((i * 4) - 3) && cratesLine[(i * 4) - 3] != ' ') {
                        listOfStacks[i - 1].add(cratesLine[(i * 4) - 3])
                    }
                }
            }
        }
        return listOfStacks
    }

    fun readMovesTriple(movesLine: String): Triple<Int, Int, Int> {
        val count = movesLine.split(" ")[1].toInt()
        val from = movesLine.split(" ")[3].toInt()
        val to = movesLine.split(" ")[5].toInt()
        return Triple(count, from, to)
    }

    fun readResult(listOfStacks: ArrayList<ArrayDeque<Char>>): String {
        var result = ""
        for (stack in listOfStacks) {
            result += stack.last()
        }
        return result
    }

    fun part1(input: List<String>): String {
        val listOfStacks = createStacks(input)

        val movesLines = input.slice(input.indexOf("") + 1 until input.size)
        for (movesLine in movesLines) {
            val (count, from, to) = readMovesTriple(movesLine)

            for (i in 1..count) {
                listOfStacks[to - 1].add(listOfStacks[from - 1].removeLast())
            }
        }

        return readResult(listOfStacks)
    }

    fun part2(input: List<String>): String {
        val listOfStacks = createStacks(input)

        val movesLines = input.slice(input.indexOf("") + 1 until input.size)
        for (movesLine in movesLines) {
            val (count, from, to) = readMovesTriple(movesLine)

            val elementsToAdd = ArrayList<Char>()
            for (i in 1..count) {
                elementsToAdd.add(listOfStacks[from - 1].removeLast())
            }
            listOfStacks[to - 1].addAll(elementsToAdd.reversed())
        }

        return readResult(listOfStacks)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readFileAsLinesUsingUseLines("src/main/resources/day_5_input_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readFileAsLinesUsingUseLines("src/main/resources/day_5_input")
    println(part1(input))
    println(part2(input))
}
