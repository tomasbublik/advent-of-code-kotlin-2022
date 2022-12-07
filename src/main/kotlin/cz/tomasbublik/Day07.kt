package cz.tomasbublik

// -1 means directory, otherwise it is a file with certain size.
data class Vertex(val size: Int, val name: String, var totalSize: Int = 0) {
    val kids = mutableSetOf<Vertex>()

    fun appendChild(vertex: Vertex) {
        kids.add(vertex)
        if (vertex.size != -1) {
            totalSize += vertex.size
        }
    }

    fun getDirectoryByName(dirName: String): Vertex {
        for (kid in kids) {
            if (kid.name == dirName) {
                return kid
            }
        }
        throw IllegalStateException("Nu such a directory exists")
    }

    fun refreshSize() {
        totalSize = 0
        for (kid in kids) {
            totalSize += if (kid.size == -1) {
                kid.totalSize
            } else {
                kid.size
            }
        }
    }
}

private const val DIR = "dir"

private const val CD = "cd"

private const val LS = "ls"
var root = Vertex(-1, "/")
var lineIndex = 0
var totalSizeBellow100000 = 0
var listOfDirsSizes = ArrayList<Int>()

fun main() {
    fun adoptKids(
        outputBuffer: ArrayList<String>,
        currentDir: Vertex
    ) {
        // var outputBuffer1 = outputBuffer
        if (outputBuffer.isNotEmpty()) {
            for (bufferedOutput in outputBuffer) {
                val splitLine = bufferedOutput.split(" ")
                if (splitLine[0] == DIR) {
                    currentDir.appendChild(Vertex(-1, splitLine[1]))
                } else {
                    currentDir.appendChild(Vertex(splitLine[0].toInt(), splitLine[1]))
                }
            }
            // buffer read, cleaning
            // outputBuffer1 = ArrayList<String>()
        }
        // return outputBuffer
    }

    fun processDirectory(
        input: List<String>,
        currentDir: Vertex
    ) {
        var outputBuffer = ArrayList<String>()
        while (lineIndex < input.size) {
            if (input[lineIndex].startsWith("$")) {
                // it is a command
                // re-calculate size!
                currentDir.refreshSize()

                // first process the previous output
                adoptKids(outputBuffer, currentDir)
                outputBuffer = ArrayList<String>()

                val splitLine = input[lineIndex].split(" ")
                lineIndex++
                if (splitLine[1] == CD) {
                    val dirName = splitLine[2]
                    if (dirName == "/") {
                        if (currentDir.name == "/") {
                            // in root already
                        } else {
                            processDirectory(input, root)
                        }
                    } else
                        if (dirName == "..") {
                            currentDir.refreshSize()
                            return
                        } else {
                            val childDir = currentDir.getDirectoryByName(dirName)
                            processDirectory(input, childDir)
                        }
                }
                if (splitLine[1] == LS) {
                    // not sure what to do, expect output?
                    outputBuffer = ArrayList<String>()
                }
            } else {
                // it is an output
                outputBuffer.add(input[lineIndex])
                lineIndex++
                currentDir.refreshSize()
            }
        }
        // first process the previous output
        adoptKids(outputBuffer, currentDir)
        outputBuffer = ArrayList<String>()
        currentDir.refreshSize()
    }

    fun createFileSystem(input: List<String>): Vertex {
        root = Vertex(-1, "/")
        lineIndex = 0
        totalSizeBellow100000 = 0

        processDirectory(input, root)
        root.refreshSize()

        return root
    }

    fun calculateTotalSize(currentDir: Vertex) {
        for (kid in currentDir.kids) {
            if (kid.size == -1) {
                if (kid.totalSize <= 100000) {
                    totalSizeBellow100000 += kid.totalSize
                }
                calculateTotalSize(kid)
            }
        }
    }

    fun traverseList(currentDir: Vertex) {
        for (kid in currentDir.kids) {
            if (kid.size == -1) {
                listOfDirsSizes.add(kid.totalSize)
                traverseList(kid)
            }
        }
    }

    fun createListOfSizes() {
        listOfDirsSizes = ArrayList()
        listOfDirsSizes.add(root.totalSize)

        traverseList(root)
    }

    fun part1(input: List<String>): Int {
        createFileSystem(input)

        if (root.totalSize <= 100000) {
            totalSizeBellow100000 += root.totalSize
        }
        calculateTotalSize(root)

        return totalSizeBellow100000
    }

    fun part2(input: List<String>): Int {
        createFileSystem(input)

        // What is the size we are looking for?
        val sizeNeededToFree = 30000000 - (70000000 - root.totalSize)
        println("We need to free:$sizeNeededToFree")

        createListOfSizes()

        var firstHigherDirSize = 0
        val sorted = listOfDirsSizes.sorted()
        for (dirSize in sorted) {
            if (dirSize > sizeNeededToFree) {
                firstHigherDirSize = dirSize
                break
            }
        }

        return firstHigherDirSize
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readFileAsLinesUsingUseLines("src/main/resources/day_7_input_test")
    // check(part1(testInput) == 95437)
    // check(part2(testInput) == 24933642)

    val input = readFileAsLinesUsingUseLines("src/main/resources/day_7_input")
    // println(part1(input))
    println(part2(input))
}