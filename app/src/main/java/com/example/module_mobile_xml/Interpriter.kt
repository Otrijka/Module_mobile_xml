package com.example.module_mobile_xml

import android.util.Log

var variableList = mutableListOf<Pair<String, Long>>()

open class Interpriter {

}


class Variable(_name: String, _data: String) : Interpriter() {
    val name: String
    val data: Long

    init {
        name = _name
        data = _data.toLong()
        addToList()
    }

    fun addToList() {
        variableList.add(Pair(name, data))
        Log.d("app", "var has added!! ${variableList[0]}")
    }
}

class Math : Interpriter() {

}