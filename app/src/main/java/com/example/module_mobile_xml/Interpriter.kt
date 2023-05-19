package com.example.module_mobile_xml

import android.util.Log
import androidx.collection.arrayMapOf
import androidx.core.text.isDigitsOnly
import checkIndex
import replaceFigureOnDots
import replaceSpacesWithCommas
import replaceWhiteSpaceOnDots
import java.util.Stack

var varNames = arrayListOf<String>()
var arrNames = arrayListOf<String>()
var arrNamesWithIndexies = arrayListOf<String>()
var variablesMap = mutableMapOf<String, Long>()
var str = ""
var lastBlock = arrayListOf<Pair<Int, String>>()
var outPutList = ArrayList<String>()

fun compile() {

    //Log.d("app", "-----------------\nCompile starting")
    //Log.d("app", "Str: " + str)
    outPutList.clear()

    if (str != "") {
        parseStr(str)
    }
    outPutList.add("\nCompile end!")

    //Log.d("app", "varMap: " + variablesMap)
    //Log.d("app", "arrNames: " + arrNames)
    //Log.d("app", "arrNamesWIndex: " + arrNamesWithIndexies)
    //Log.d("app", "varNames: " + varNames)
    //Log.d("app", "varMap: " + variablesMap)
    Log.d("app", "Compile end\n-----------------")

}


fun parseStr(string: String) {
    var str = string.replace(",", " ").trim()

    if (str.toCharArray()[0] == '[') {
        str = str.drop(1)
    }

    if (str.toCharArray()[str.length - 1] == ']') {
        str = str.dropLast(1)
    }

    str = str.trim()
    str = replaceFigureOnDots(str)
    str = replaceSpacesWithCommas(str)

    Log.d("app", "currentStr: " + str)
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
            "printArray",
            ">",
            "=>",
            "<",
            "<=",
            "==",
            "endIf",
            "endWhile"
        )

    fun popAtStack(varStack: Stack<String>): String {
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
            if ("_" in i && "," !in i && "[" !in i && "]" !in i){
                val index = if (i.split("_").last().isDigitsOnly()) i.split("_").last() else variablesMap[i.split("_").last()].toString()
                varStack.add("${i.split("_").first()}_$index")
            }else{
                varStack.add(i)
            }
        } else {

            when (i) {
                "=" -> {
                    val rightVar = popAtStack(varStack)
                    val leftVar = varStack.pop()
                    if (leftVar in arrNames){
                        val index = checkIndex(varStack.pop())
                        variablesMap.put("${leftVar}_${index}", rightVar.toLong())
                    }else{
                        variablesMap.put(leftVar, rightVar.toLong())
                    }
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
                "printArray" -> {
                    val rightVar = varStack.pop()
                    var tempStr = "["
                    for (name in variablesMap.keys) {
                        if ("${rightVar}_" in name) {
                            tempStr += variablesMap[name].toString() + " "
                        }
                    }
                    tempStr = tempStr.trimEnd() + "]"
                    outPutList.add(tempStr)
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
                        parseStr("$logicExpression $DO endWhile")
                    }
                }
            }
        }
    }
}
