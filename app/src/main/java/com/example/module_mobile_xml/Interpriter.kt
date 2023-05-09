package com.example.module_mobile_xml

import android.util.Log
import java.util.Stack

var varNames = arrayListOf<String>()
var variablesMap = mutableMapOf<String, Long>()
var str = ""
var lastBlock = arrayListOf<Pair<Int,String>>()
var outPutList = ArrayList<String>()

fun compile() {
    Log.d("app", "-----------------\nCompile starting")

    Log.d("app", "str: " + str)

    var varStack = Stack<String>()

    parseStr(varStack)
    Log.d("app", "varMap: " + variablesMap)
    //variablesMap.clear()
    //varNames.clear()
    Log.d("app", "varStack: " + varStack.toString())

    Log.d("app", "Compile end\n-----------------")
}


fun parseStr(varStack: Stack<String>) {
    outPutList.clear()
    val actionList = arrayListOf("+", "-", "*", "/", "=" ,"^", "print")

    for (i in str.split(" ")) {

        if (i !in actionList && i != "") {
            varStack.add(i)
        } else {

            when (i) {
                "=" -> {
                    val rightVar = if (varStack.peek() in variablesMap.keys) variablesMap[varStack.pop()].toString() else varStack.pop()
                    val leftVar = varStack.pop()
                    variablesMap.put(leftVar, rightVar.toLong())
                }
                "+" -> {
                    val rightVar = if (varStack.peek() in variablesMap.keys) variablesMap[varStack.pop()].toString() else varStack.pop()
                    val leftVar = if (varStack.peek() in variablesMap.keys) variablesMap[varStack.pop()].toString() else varStack.pop()
                    varStack.push((leftVar.toLong() + rightVar.toLong()).toString())
                }
                "-" -> {
                    val rightVar = if (varStack.peek() in variablesMap.keys) variablesMap[varStack.pop()].toString() else varStack.pop()
                    val leftVar = if (varStack.peek() in variablesMap.keys) variablesMap[varStack.pop()].toString() else varStack.pop()
                    varStack.push((leftVar.toLong() - rightVar.toLong()).toString())
                }
                "*" -> {
                    val rightVar = if (varStack.peek() in variablesMap.keys) variablesMap[varStack.pop()].toString() else varStack.pop()
                    val leftVar = if (varStack.peek() in variablesMap.keys) variablesMap[varStack.pop()].toString() else varStack.pop()
                    varStack.push((leftVar.toLong() * rightVar.toLong()).toString())
                }
                "/" -> {
                    val rightVar = if (varStack.peek() in variablesMap.keys) variablesMap[varStack.pop()].toString() else varStack.pop()
                    val leftVar = if (varStack.peek() in variablesMap.keys) variablesMap[varStack.pop()].toString() else varStack.pop()
                    varStack.push((leftVar.toLong() / rightVar.toLong()).toString())
                }
                "^" -> {
                    val rightVar = if (varStack.peek() in variablesMap.keys) variablesMap[varStack.pop()].toString() else varStack.pop()
                    val leftVar = if (varStack.peek() in variablesMap.keys) variablesMap[varStack.pop()].toString() else varStack.pop()
                    varStack.push((Math.pow(leftVar.toDouble(),rightVar.toDouble()).toLong()).toString())
                }
                "print" -> {
                    val rightVar = varStack.pop()
                    outPutList.add(variablesMap[rightVar].toString())
                    Log.d("app", "$rightVar = " + variablesMap[rightVar].toString())
                }
            }
        }
    }
}
