package org.example

class Gamer(gname: String) {
    var name: String = gname
    var points: Int = 0

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