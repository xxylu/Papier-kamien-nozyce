import java.io.File
import java.io.IOException
import kotlin.random.Random

class Game(val gamer: Gamer) {
    var bestPlayer: String
    var bestScore: Int
    private val filename = "bs.csv"


    fun play(nRounds: Int) {
        val file = FileCSV()
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

        file.saveScore(gamer.name, gamer.points)
        if (bestScore < gamer.points) {
            print("New record!!!\n\n")
        }
        file.readAllScores(gamer.name)
    }

    init {
        val file = FileCSV()
        bestPlayer = file.readBestScore()?.first ?: "empty"
        bestScore = file.readBestScore()?.second ?: 0
    }
}