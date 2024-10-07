
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

            number = Random.nextInt(0, 2)
            println(" Type \"Rock\", \"Paper\" or \"Scissors\"")
            guess = readlnOrNull().toString()
            if(guess in answers) {
                if (answers.indexOf(guess) == number) {
                    println("I thought about ${answers[number]}, so it's a tie! No points added")
                } else if (
                    answers.indexOf(guess) == 0 && number == 2
                    || answers.indexOf(guess) == 1 && number == 0
                    || answers.indexOf(guess) == 2 && number == 1
                    ) {
                    println("I thought about ${answers[number]}, and you typed $guess! Point for you.")
                    gamer.addPoint()
                }
                else {
                    println("I thought about ${answers[number]}, and you typed $guess! Point for me.")
                    gamer.minusPoint()
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

    //funcja zapisuje tylko dane gry jeżeli gracz pobije swój rekord, nazwa gracza nie powtarza się w tabeli
    private fun saveScore(nazwaPliku: String, playername: String, playerscore: Int) {
        try {
            val file = File(nazwaPliku)
            val allScores : HashMap<String, Int> = HashMap()
            if(file.length().toInt() == 0){
                file.writeText("$playername,$playerscore\n")
            }
            var lines = file.readLines()
            file.writeText("$playername,$playerscore\n")
            file.appendText(lines.joinToString("\n"))
            file.appendText("\n")
            lines = file.readLines()
            file.writeText("")
                lines.forEach { line ->
                    val data = line.split(",")
                    if (data[0] == gamer.name && gamer.points >= data[1].toInt()) {
                        allScores[gamer.name] = gamer.points
                    } else {
                        val name = data[0]
                        val score = data[1].toInt()
                        allScores[name] = score
                    }
                }
                allScores.toList()
                    .sortedByDescending { (_, score) -> score }
                    .forEach {
                            (name, score) ->
                        file.appendText("$name,$score\n")
                    }
        } catch (e: IOException) {
            println("Error: ${e.message}")
        }
    }
    private fun readAllScores(nazwaPliku: String) {
        println("Leaderboard: \n")
        val allScores : HashMap<String, Int> = HashMap()

        try {
            var i: Int = 1
            val file = File(nazwaPliku)
            val lines = file.readLines()
            lines.forEach { line ->
                val data = line.split(",")
                if (data.size == 2) {
                    val name = data[0]
                    val score = data[1].toInt()
                    //allScores
                    if (name == gamer.name){
                        println("$i $name: $score <- your best score")
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
            val linia = file.readLines()
            if(linia.size == 0) {
                return null
            }
            val line0 = linia[0]
                    val data = line0.split(",")
                    if (data.size >= 2) {
                        val name = data[0]
                        val score =
                            data[1].toIntOrNull() ?: 0 //gdy nie wczyta inta zwraca null, null zamieniony na zero
                        return Pair(name, score)
                    } else {
                        println("not enough data")
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