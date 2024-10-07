package org.example
import Game

fun main() {
    println("Your name:")
    val gname: String = readlnOrNull()?:""
    val gamer = Gamer(gname)
    println("How many rounds:")
    val nrounds = readlnOrNull()?.toIntOrNull() ?: 1
    val game = Game(gamer)
    game.play(nrounds)

}