package cz.tomasbublik

fun main() {
    fun addToGroup(
        groupedCalories: MutableMap<Int, List<String>>,
        group: MutableList<String>
    ) {
        groupedCalories[group.sumOf { it.toInt() }] = group
    }

    fun getGroupedCalories(input: List<String>): MutableMap<Int, List<String>> {
        val groupedCalories: MutableMap<Int, List<String>> = HashMap()
        var group: MutableList<String> = ArrayList()
        for (calories in input) {
            if (calories.isNotEmpty()) {
                group.add(calories)
            } else {
                addToGroup(groupedCalories, group)
                group = ArrayList()
            }
        }
        if (group.size > 0) {
            addToGroup(groupedCalories, group)
        }
        return groupedCalories
    }

    fun part1(input: List<String>): Int {
        val groupedCalories: MutableMap<Int, List<String>> = getGroupedCalories(input)

        return groupedCalories.keys.max()
    }

    fun part2(input: List<String>): Int {
        val groupedCalories: MutableMap<Int, List<String>> = getGroupedCalories(input)
        val sortedCalories = groupedCalories.keys.sorted().reversed()

        return sortedCalories.take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readFileAsLinesUsingUseLines("src/main/resources/day_1_input_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readFileAsLinesUsingUseLines("src/main/resources/day_1_input")
    println(part1(input))
    println(part2(input))
}
