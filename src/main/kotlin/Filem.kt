package org.example

import java.io.File
import java.io.IOException

class Filem {

    private fun saveScore(nazwaPliku: String, name: String, score: Int) {
        try {
            val file = File(nazwaPliku)
            file.writeText("$name,$score")
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
                println("empty file")
                return null
            }
        } catch (e: IOException) {
            println("error: ${e.message}")
            return null
        }
    }
}
