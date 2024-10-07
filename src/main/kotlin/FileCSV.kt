import java.io.File
import java.io.IOException
class FileCSV {
    private val file = File("bs.csv")

    init {
        file
    }

    //funcja zapisuje tylko dane gry jeżeli gracz pobije swój rekord, nazwa gracza nie powtarza się w tabeli
    fun saveScore(playername: String, playerscore: Int) {
        try {
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
                if (data[0] == playername && playerscore >= data[1].toInt()) {
                    allScores[playername] = playerscore
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
    fun readAllScores(playername: String) {
        println("Leaderboard: \n")

        try {
            var i = 1
            val lines = file.readLines()
            lines.forEach { line ->
                val data = line.split(",")
                if (data.size == 2) {
                    val name = data[0]
                    val score = data[1].toInt()
                    //allScores
                    if (name == playername){
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

    fun readBestScore(): Pair<String, Int>? { //zwracamy parę lub null.
        try {
            if (!file.exists()) {
                file.createNewFile()
                return null
            }
            val linia = file.readLines()
            if(linia.isEmpty()) {
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
}