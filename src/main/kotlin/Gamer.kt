class Gamer(gName: String) {
    var name: String = gName
    var points = 0

    fun addPoint(){
        points++
    }
    fun minusPoint(){
        points--
    }

    init {
        points
    }
}