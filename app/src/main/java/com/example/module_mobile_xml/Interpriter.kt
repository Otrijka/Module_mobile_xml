package com.example.module_mobile_xml

import android.util.Log
import java.util.Stack

var variablesMap = mutableMapOf<String,Long>()

class Interpriter {
    var variablesStack = Stack<String>()
    var actionsStack = Stack<String>()
}


class Variable(_name: String, _data: String) {
    val name: String
    val data: Long

    init {
        name = _name
        data = _data.toLong()
        addToList()
    }

    fun addToList() {
        variablesMap.put(name,data)
        Log.d("app", variablesMap.toString())
    }
}
