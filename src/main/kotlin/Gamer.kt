package org.example

class Gamer(gname: String) {
    var name: String = gname
    var points: Int

    fun addPoint(){
        points++
    }
    fun minusPoint(){
        points--
    }

    init {
        points = 0
    }
}