package com.example.module_mobile_xml

import android.util.Log
import replaceFigureOnDots
import replaceWhiteSpaceOnDots
import java.util.Stack

var varNames = arrayListOf<String>()
var arrNames = arrayListOf<String>()
var variablesMap = mutableMapOf<String, Long>()
var str = ""
var lastBlock = arrayListOf<Pair<Int, String>>()
var outPutList = ArrayList<String>()

fun compile() {

    Log.d("app", "-----------------\nCompile starting")
    outPutList.clear()
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

    str = str.trim()
    str = replaceFigureOnDots(str)
    str = replaceWhiteSpaceOnDots(str)

    Log.d("app", "nextStr: " + str)
    val varStack = Stack<String>()

    val actionList =
        arrayListOf(
            "+",
            "-",
            "*",
            "/",
            "=",
            "^",
            "%",
            "print",
            ">",
            "=>",
            "<",
            "<=",
            "==",
            "endIf",
            "endWhile"
        )

    fun popAtStack(varStack : Stack<String>): String {
        return if (varStack.peek() in variablesMap.keys) variablesMap[varStack.pop()].toString() else varStack.pop()
    }

    fun parseLogicExperssion(string: String): String {
        val conditionList =
            arrayListOf(
                ">",
                "=>",
                "<",
                "<=",
                "==",
            )
        var str = string.replace(",", " ").trim().drop(1).dropLast(1)

        str = str.trim()
        str = replaceWhiteSpaceOnDots(str)

        Log.d("app", "logicExp: " + str)
        var varStack = Stack<String>()

        for (i in str.split(" ")) {
            if (i !in conditionList && i != "" && i != "[" && i != "]" && i != " ") {
                varStack.add(i)
            } else {
                when (i) {
                    ">" -> {
                        val rightVar = popAtStack(varStack)
                        val leftVar = popAtStack(varStack)
                        return (leftVar.toLong() > rightVar.toLong()).toString()
                    }
                    ">=" -> {
                        val rightVar = popAtStack(varStack)
                        val leftVar = popAtStack(varStack)
                        return (leftVar.toLong() >= rightVar.toLong()).toString()
                    }
                    "<" -> {
                        val rightVar = popAtStack(varStack)
                        val leftVar = popAtStack(varStack)
                        return (leftVar.toLong() < rightVar.toLong()).toString()
                    }
                    "<=" -> {
                        val rightVar = popAtStack(varStack)
                        val leftVar = popAtStack(varStack)
                        return (leftVar.toLong() <= rightVar.toLong()).toString()
                    }
                    "==" -> {
                        val rightVar = popAtStack(varStack)
                        val leftVar = popAtStack(varStack)
                        return (leftVar.toLong() == rightVar.toLong()).toString()
                    }
                }
            }
        }

        return "error"
    }

    for (i in str.split(" ")) {

        if (i !in actionList && i != "" && i != "[" && i != "]" && i != " ") {
            varStack.add(i)
        } else {

            when (i) {
                "=" -> {
                    val rightVar = popAtStack(varStack)
                    val leftVar = varStack.pop()
                    variablesMap.put(leftVar, rightVar.toLong())
                }
                "+" -> {
                    val rightVar = popAtStack(varStack)
                    val leftVar = popAtStack(varStack)
                    varStack.push((leftVar.toLong() + rightVar.toLong()).toString())
                }
                "-" -> {
                    val rightVar = popAtStack(varStack)
                    val leftVar = popAtStack(varStack)
                    varStack.push((leftVar.toLong() - rightVar.toLong()).toString())
                }
                "*" -> {
                    val rightVar = popAtStack(varStack)
                    val leftVar = popAtStack(varStack)
                    varStack.push((leftVar.toLong() * rightVar.toLong()).toString())
                }
                "/" -> {
                    val rightVar = popAtStack(varStack)
                    val leftVar = popAtStack(varStack)
                    varStack.push((leftVar.toLong() / rightVar.toLong()).toString())
                }
                "^" -> {
                    val rightVar = popAtStack(varStack)
                    val leftVar = popAtStack(varStack)
                    varStack.push(
                        (Math.pow(leftVar.toDouble(), rightVar.toDouble()).toLong()).toString()
                    )
                }
                "%" -> {
                    val rightVar = popAtStack(varStack)
                    val leftVar = popAtStack(varStack)
                    varStack.push(
                        (leftVar.toLong() % rightVar.toLong()).toString()
                    )
                }
                ">" -> {
                    val rightVar = popAtStack(varStack)
                    val leftVar = popAtStack(varStack)
                    varStack.push((leftVar.toLong() > rightVar.toLong()).toString())
                }
                ">=" -> {
                    val rightVar = popAtStack(varStack)
                    val leftVar = popAtStack(varStack)
                    varStack.push((leftVar.toLong() >= rightVar.toLong()).toString())
                }
                "<" -> {
                    val rightVar = popAtStack(varStack)
                    val leftVar = popAtStack(varStack)
                    varStack.push((leftVar.toLong() < rightVar.toLong()).toString())
                }
                "<=" -> {
                    val rightVar = popAtStack(varStack)
                    val leftVar = popAtStack(varStack)
                    varStack.push((leftVar.toLong() <= rightVar.toLong()).toString())
                }
                "==" -> {
                    val rightVar = popAtStack(varStack)
                    val leftVar = popAtStack(varStack)
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
                "endWhile" -> {
                    val DO = varStack.pop()
                    val logicExpression = varStack.pop()
                    val flag = parseLogicExperssion(logicExpression).toBoolean()
                    if (flag == true) {
                        parseStr(DO)
                        parseStr( "$logicExpression $DO endWhile")
                    }
                }
            }
        }
    }
}
