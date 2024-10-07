
import org.example.Gamer
import java.io.File
import java.io.IOException
import kotlin.random.Random

class Game(val gamer: Gamer) {
    var bestPlayer: String
    var bestScore: Int
    private val filename = "bs.csv"


    fun play(nRounds: Int) {
        println("Hi ${gamer.name}, let's play $nRounds rounds of rock, paper, scissors.")
        if(bestPlayer == "empty"){
            println("There is no best player yet.")
        } else {
            println("Best Player is $bestPlayer, with $bestScore")
        }

        var number: Int
        var guess: String
        val answers = listOf("Rock", "Paper", "Scissors")

        for (i in 1..nRounds) {

            number = Random.nextInt(0, 3)
            println(" Type \"Rock\", \"Paper\" or \"Scissors\"")
            guess = readlnOrNull().toString()
            if(guess in answers) {
                if (answers.indexOf(guess) == number) {
                    println("I thought about ${answers[number]}, so it's a tie! No points added")
                } else {
                    gamer.addPoint()
                }
            }
        }
        println("Total score of ${gamer.name}: ${gamer.points}")

        saveScore(filename, gamer.name, gamer.points)
        if (bestScore < gamer.points) {
            print("New record!!!\n\n")
        }
        readAllScores(filename)
    }

    private fun saveScore(nazwaPliku: String, playername: String, playerscore: Int) {
        try {
            val file = File(nazwaPliku)
            val allScores : HashMap<String, Int> = HashMap()
            file.appendText("\n$playername,$playerscore")
            val lines = file.readLines()
            file.writeText("")
            lines.forEach { line ->
                val data = line.split(",")
                if (data.size == 2) {
                    val name = data[0]
                    val score = data[1].toInt()
                    allScores.put(name, score)
                }
            }
            //allScores.toSortedMap(compareBy<Int> {allScores[it].thenBy {it}})

            for ((key,value ) in allScores) {
                    file.appendText("$key,$value\n")
            }
        } catch (e: IOException) {
            println("Error: ${e.message}")
        }
    }
    private fun readAllScores(nazwaPliku: String) {
        println("Leaderboard: \n")
        try {
            var i: Int = 1
            val file = File(nazwaPliku)
            val lines = file.readLines()
            lines.forEach { line ->
                val data = line.split(",")
                if (data.size == 2) {
                    val name = data[0]
                    val score = data[1].toInt()
                    if (name == gamer.name){
                        println("$i $name: $score <- you")
                    } else {
                        println( "$i $name: $score")
                    }
                    i++
                } else {
                    println("Not enough data.")
                }
            }
        } catch (e: IOException) {
            println("Error: ${e.message}")
        }
    }

    private fun readBestScore(nazwaPliku: String): Pair<String, Int>? { //zwracamy parę lub null.
        try {
            val file = File(nazwaPliku)
            if (!file.exists()) {
                file.createNewFile()
                return null
            }
            val linia = file.readText().trim()
            if (linia.isNotEmpty()) {
                val data = linia.split(",")
                if (data.size >= 2) {
                    val name = data[0]
                    val score = data[1].toIntOrNull() ?: 0 //gdy nie wczyta inta zwraca null, null zamieniony na zero
                    return Pair(name, score)
                } else {
                    println("not enough data")
                    return null
                }
            } else {
                // not enough data
                return null
            }
        } catch (e: IOException) {
            println("error: ${e.message}")
            return null
        }
    }

    init {
        bestPlayer = readBestScore(filename)?.first ?: "empty"
        bestScore = readBestScore(filename)?.second ?: 0
    }
}