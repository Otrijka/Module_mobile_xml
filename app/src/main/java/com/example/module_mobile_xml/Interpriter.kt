package com.example.module_mobile_xml

import android.util.Log
import replaceWhiteSpaceOnDots
import java.util.Stack

var varNames = arrayListOf<String>()
var variablesMap = mutableMapOf<String, Long>()
var str = ""
var lastBlock = arrayListOf<Pair<Int, String>>()
var outPutList = ArrayList<String>()

fun compile() {

    Log.d("app", "-----------------\nCompile starting")

    parseStr(str)

    Log.d("app", "varMap: " + variablesMap)
    Log.d("app", "Compile end\n-----------------")

}


fun parseStr(string: String) {
    Log.d("app", "StartStr: " + string)
    var str = string.replace(",", " ").trim()

    if (str.toCharArray()[0] == '[') {
        str = str.drop(1)
    }

    if (str.toCharArray()[str.length - 1] == ']') {
        str = str.dropLast(1)
    }

    str = replaceWhiteSpaceOnDots(str)
    Log.d("app", "nextStr: " + str)
    var varStack = Stack<String>()

    outPutList.clear()
    val actionList =
        arrayListOf("+", "-", "*", "/", "=", "^", "print", ">", "=>", "<", "<=", "==", "endIf")

    fun popAtStack(): String {
        return if (varStack.peek() in variablesMap.keys) variablesMap[varStack.pop()].toString() else varStack.pop()
    }

    for (i in str.split(" ")) {

        if (i !in actionList && i != "" && i != "[" && i != "]" && i != " ") {
            varStack.add(i)
        } else {

            when (i) {
                "=" -> {
                    val rightVar = popAtStack()
                    val leftVar = varStack.pop()
                    variablesMap.put(leftVar, rightVar.toLong())
                }
                "+" -> {
                    val rightVar = popAtStack()
                    val leftVar = popAtStack()
                    varStack.push((leftVar.toLong() + rightVar.toLong()).toString())
                }
                "-" -> {
                    val rightVar = popAtStack()
                    val leftVar = popAtStack()
                    varStack.push((leftVar.toLong() - rightVar.toLong()).toString())
                }
                "*" -> {
                    val rightVar = popAtStack()
                    val leftVar = popAtStack()
                    varStack.push((leftVar.toLong() * rightVar.toLong()).toString())
                }
                "/" -> {
                    val rightVar = popAtStack()
                    val leftVar = popAtStack()
                    varStack.push((leftVar.toLong() / rightVar.toLong()).toString())
                }
                "^" -> {
                    val rightVar = popAtStack()
                    val leftVar = popAtStack()
                    varStack.push(
                        (Math.pow(leftVar.toDouble(), rightVar.toDouble()).toLong()).toString()
                    )
                }
                ">" -> {
                    val rightVar = popAtStack()
                    val leftVar = popAtStack()
                    varStack.push((leftVar.toLong() > rightVar.toLong()).toString())
                }
                ">=" -> {
                    val rightVar = popAtStack()
                    val leftVar = popAtStack()
                    varStack.push((leftVar.toLong() >= rightVar.toLong()).toString())
                }
                "<" -> {
                    val rightVar = popAtStack()
                    val leftVar = popAtStack()
                    varStack.push((leftVar.toLong() < rightVar.toLong()).toString())
                }
                "<=" -> {
                    val rightVar = popAtStack()
                    val leftVar = popAtStack()
                    varStack.push((leftVar.toLong() <= rightVar.toLong()).toString())
                }
                "==" -> {
                    val rightVar = popAtStack()
                    val leftVar = popAtStack()
                    varStack.push((leftVar.toLong() == rightVar.toLong()).toString())
                }
                "print" -> {
                    val rightVar = varStack.pop()
                    outPutList.add(variablesMap[rightVar].toString())
                    Log.d("app", "$rightVar = " + variablesMap[rightVar].toString())
                }
                "endIf" -> {
                    var ELSE = varStack.pop()
                    var THEN = varStack.pop()
                    var FLAG = varStack.pop().toBoolean()
                    if (FLAG == true) {
                        THEN = THEN.replace(",", " ").trim()
                        parseStr(THEN)
                    } else {
                        ELSE = ELSE.replace(",", " ").trim()
                        parseStr(ELSE)
                    }
                }
            }
        }
    }
}
