import androidx.core.text.isDigitsOnly
import com.example.module_mobile_xml.arrayNames
import com.example.module_mobile_xml.str
import com.example.module_mobile_xml.varNames
import com.example.module_mobile_xml.variablesMap
import java.util.*

fun normilizeString(text: String): String {
    var newText = text.filter { !it.isWhitespace() }
    newText = newText.replace("([+\\-*/^%])".toRegex(), " $1 ")
    newText = newText.replace("([(])".toRegex(), "$1 ")
    newText = newText.replace("([)])".toRegex(), " $1")

    var output = ""
    for (i in newText.split(" ")) {
        var temp = ""
        for (j in i.split("_")) {
            if (variablesMap.containsKey(j)) {
                temp += "_${variablesMap[j].toString()}"
            } else if (j.isDigitsOnly() && (temp + "_" + j) in variablesMap) {
                temp += "_${j}"
            } else {
                temp += "$j"
            }
        }
        if (temp.equals("")) {
            output += "$i "
        } else {
            output += "$temp "
        }
    }
    return output
}

fun replaceWhiteSpaceOnDots(input: String): String {
    val regex = Regex("""\[[^\[\]]*(?:\[[^\[\]]*][^\[\]]*)*]""")

    val result = regex.replace(input) { match ->
        match.value.replace(" ", ",")
    }
    return result
}


fun replaceFigureOnDots(input: String): String {
    val regex = Regex("\\{\\s*(.*?)\\s*\\}")
    val output = regex.replace(input) { matchResult ->
        matchResult.value.replace(" ", ",")
    }
    return output
}

fun checkNameInVarNames() {
    for (i in varNames) {
        if (i !in str) {
            try {
                varNames.remove(i)
            } catch (e: Exception) {
                // TODO: error varName removing
            }
        }
    }
}

fun checkNameInArrayNames() {
    for (i in arrayNames) {
        if (i !in str) {
            try {
                arrayNames.remove(i)
            } catch (e: java.lang.Exception) {
                // TODO: error arrName removing
            }
        }
    }
}

fun checkOnArray(str: String): Boolean {
    for (arr in arrayNames) {
        if (str in arr) {
            return true
        }
    }
    return false
}

fun checkOnArrayReverse(str: String): Boolean {
    for (arr in arrayNames) {
        if (arr in str) {
            return true
        }
    }
    return false
}

fun toReversePolishNotation(expression: String): String {
    val outputQueue = mutableListOf<String>()
    val operatorStack = Stack<String>()

    val operators = mapOf(
        "+" to 1,
        "-" to 1,
        "%" to 1,
        "*" to 2,
        "/" to 2,
        "^" to 3
    )

    for (token in expression.split(" ")) {
        when {
            token.matches(Regex("\\d+")) -> outputQueue.add(token) // Если токен - число, добавляем его в очередь вывода
            token in varNames || checkOnArrayReverse(token) -> outputQueue.add(token) // Если токен - название переменной, добавляем его в очередь вывода
            operators.containsKey(token) -> { // Если токен - оператор
                while (!operatorStack.isEmpty() && operators[operatorStack.peek()] ?: 0 >= operators[token] ?: 0) {
                    outputQueue.add(operatorStack.pop()) // Извлекаем операторы из стека и добавляем их в очередь вывода
                }
                operatorStack.push(token) // Добавляем текущий оператор в стек
            }
            token == "(" -> operatorStack.push(token) // Если токен - открывающая скобка, добавляем ее в стек
            token == ")" -> { // Если токен - закрывающая скобка
                while (operatorStack.peek() != "(") {
                    outputQueue.add(operatorStack.pop()) // Извлекаем операторы из стека и добавляем их в очередь вывода до тех пор, пока не встретим открывающую скобку
                }
                operatorStack.pop() // Удаляем открывающую скобку из стека
            }
            else -> throw IllegalArgumentException("Unknown token: $token") // Если токен не является числом, оператором или скобкой, выбрасываем исключение
        }
    }

    while (!operatorStack.isEmpty()) {
        outputQueue.add(operatorStack.pop()) // Извлекаем все оставшиеся операторы из стека и добавляем их в очередь вывода
    }

    return outputQueue.joinToString(" ")
}

fun checkInMapKeys(str: String): String {
    var temp = ""
    for (word in str.split(" ")) {
        if (word in variablesMap.keys) {
            temp += variablesMap[word].toString() + " "
        } else {
            temp += word
        }
    }
    return temp
}