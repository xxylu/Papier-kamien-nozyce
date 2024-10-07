package org.example
import Game

fun main() {
    println("Your name:")
    val gName: String = readlnOrNull()?:""
    val gamer = Gamer(gName)
    println("How many rounds:")
    val nRounds = readlnOrNull()?.toIntOrNull() ?: 1
    val game = Game(gamer)
    game.play(nRounds)

}