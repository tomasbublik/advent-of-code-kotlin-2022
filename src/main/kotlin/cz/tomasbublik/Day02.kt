package cz.tomasbublik

const val LOST = 0
const val DRAW = 3
const val WON = 6

const val ROCK_VALUE = 1
const val PAPER_VALUE = 2
const val SCISSORS_VALUE = 3

const val ROCK = "A"
const val PAPER = "B"
const val SCISSORS = "C"

const val MY_ROCK = "X"
const val MY_PAPER = "Y"
const val MY_SCISSORS = "Z"

const val MY_LOST = "X"
const val MY_DRAW = "Y"
const val MY_WIN = "Z"

fun main() {

    fun game(he: String, me: String): Int {
        if (he == ROCK && me == MY_ROCK) {
            return DRAW + ROCK_VALUE
        }
        if (he == ROCK && me == MY_PAPER) {
            return WON + PAPER_VALUE
        }

        if (he == ROCK && me == MY_SCISSORS) {
            return LOST + SCISSORS_VALUE
        }


        if (he == PAPER && me == MY_ROCK) {
            return LOST + ROCK_VALUE
        }
        if (he == PAPER && me == MY_PAPER) {
            return DRAW + PAPER_VALUE
        }

        if (he == PAPER && me == MY_SCISSORS) {
            return WON + SCISSORS_VALUE
        }


        if (he == SCISSORS && me == MY_ROCK) {
            return WON + ROCK_VALUE
        }
        if (he == SCISSORS && me == MY_PAPER) {
            return LOST + PAPER_VALUE
        }

        if (he == SCISSORS && me == MY_SCISSORS) {
            return DRAW + SCISSORS_VALUE
        }

        throw IllegalStateException("This cannot happen! :-) he: $he ; me: $me")
    }

    fun part1(input: List<String>): Int {
        val pairList = ArrayList<Pair<String, String>>()
        for (i in input) {
            val split = i.split(" ")
            pairList.add(Pair(split[0], split[1]))
        }
        val listOfMyResults = ArrayList<Int>()
        for (pair in pairList) {
            val gameResult: Int = game(pair.first, pair.second)
            listOfMyResults.add(gameResult)
        }
        return listOfMyResults.sum()
    }

    fun guidance(he: String, me: String): String {
        if (he == ROCK && me == MY_LOST) {
            return MY_SCISSORS
        }
        if (he == PAPER && me == MY_LOST) {
            return MY_ROCK
        }
        if (he == SCISSORS && me == MY_LOST) {
            return MY_PAPER
        }


        if (he == ROCK && me == MY_DRAW) {
            return MY_ROCK
        }
        if (he == PAPER && me == MY_DRAW) {
            return MY_PAPER
        }
        if (he == SCISSORS && me == MY_DRAW) {
            return MY_SCISSORS
        }


        if (he == ROCK && me == MY_WIN) {
            return MY_PAPER
        }
        if (he == PAPER && me == MY_WIN) {
            return MY_SCISSORS
        }
        if (he == SCISSORS && me == MY_WIN) {
            return MY_ROCK
        }
        throw IllegalStateException("This cannot happen during guided pick! :-) he: $he ; me: $me")
    }

    fun part2(input: List<String>): Int {
        val pairList = ArrayList<Pair<String, String>>()
        for (i in input) {
            val split = i.split(" ")
            pairList.add(Pair(split[0], split[1]))
        }
        val listOfMyResults = ArrayList<Int>()
        for (pair in pairList) {
            val guidedPick: String = guidance(pair.first, pair.second)
            val gameResult: Int = game(pair.first, guidedPick)
            listOfMyResults.add(gameResult)
        }
        return listOfMyResults.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readFileAsLinesUsingUseLines("src/main/resources/day_2_input_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readFileAsLinesUsingUseLines("src/main/resources/day_2_input")
    println(part1(input))
    println(part2(input))
}
