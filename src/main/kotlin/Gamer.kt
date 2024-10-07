package org.example

class Gamer(gname: String) {
    var name: String
    var points: Int

    fun addPoint(){
        points++
    }
    fun minusPoint(){
        points--
    }

    init {
        name = gname
        points = 0
    }
}