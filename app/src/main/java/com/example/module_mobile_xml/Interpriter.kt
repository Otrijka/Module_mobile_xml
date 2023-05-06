package com.example.module_mobile_xml

import android.util.Log
import java.util.Stack

var varNames = arrayListOf<String>()
var variablesMap = mutableMapOf<String, Long>()
var str = ""

fun compile() {
    Log.d("app", "Compile starting")

    Log.d("app", "str: " + str)

    var varStack = Stack<String>()
    var actionStack = Stack<String>()

    parseStr(varStack, actionStack)
    variablesMap.clear()
    Log.d("app", "varStack: " + varStack.toString())

    Log.d("app", "Compile end")
}


fun parseStr(varStack: Stack<String>, actionStack: Stack<String>) {

    //str = "a 1 = b 2 = c 3 = a 10 + b 10 + c 10 + a print b print c print "
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
                    Log.d("app", "$rightVar = " + variablesMap[rightVar].toString())
                }
            }
        }
    }
}
