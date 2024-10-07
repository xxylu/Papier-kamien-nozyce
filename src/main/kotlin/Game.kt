
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
        println("Best Player is $bestPlayer, with $bestScore")

        var number: Int
        var guess: String
        val answers = listOf("Rock", "Paper", "Scissors")

        for (i in 1..nRounds) {

            number = Random.nextInt(0, 3);
            println(" Type \"Rock\", \"Paper\" or \"Scissors\"")
            guess = readlnOrNull().toString()
            if(guess in answers) {
                if (answers.indexOf(guess) == number) {
                    println("I thought about ${answers[number]}, so it's a tie! No points added")
                }
            } else {
                gamer.addPoint()
                println("You typed wrong answer")
            }
        }
        println("Total score of ${gamer.name}: ${gamer.points}")
        saveScore(filename, gamer.name, gamer.points)
        if (bestScore < gamer.points) {
            print("new record")
        }
        println("Leaderboard \n\n\n")
    }

    private fun saveScore(nazwaPliku: String, name: String, score: Int) {
        try {
            val file = File(nazwaPliku)
            file.writeText("$name,$score")
        } catch (e: IOException) {
            println("Error: ${e.message}")
        }
    }
    private fun readAllScores(nazwaPliku: String) {
        try {
            val file = File(nazwaPliku)
            val lines = file.readLines()
            lines.forEach { line ->
                val data = line.split(",")
                val name = data[0]
                val score = data[1].toInt()
                println("$name: $score")
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
                println("There is no best player yet.")
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