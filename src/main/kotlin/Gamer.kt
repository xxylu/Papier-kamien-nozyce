class Gamer(gname: String) {
    var name: String = gname
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