import com.example.module_mobile_xml.*
import java.util.*

fun normilizeString(text: String): String {
    var newText = text.filter { !it.isWhitespace() }
    newText = newText.replace("([+\\-*/^%])".toRegex(), " $1 ")
    newText = newText.replace("([(])".toRegex(), "$1 ")
    newText = newText.replace("([)])".toRegex(), " $1")

    return newText
}

fun reverseVarToIndex(str : String) : String{
    var mainTemp = ""

    for (word in str.split(" ")){
        if ("_" in word){
            val name = word.split("_").first()
            var index = word.split("_").last()

            if (index in varNames){
                index = variablesMap[index].toString()
            }

            mainTemp += "${name}_$index "
        }else{
            mainTemp += "$word "
        }
    }
    return mainTemp.trim()
}


fun replaceWhiteSpaceOnDots(input: String) : String{

    val regex = Regex("""\[[^\[\]]*\]""")

    val result = regex.replace(input) { match ->
        match.value.replace(" ", ",")
    }
    return result
}

fun replaceSpacesWithCommas(input: String): String {
    val result = StringBuilder()
    val stack = mutableListOf<Int>()
    var insideBracket = false

    for (c in input) {
        if (c == '[') {
            stack.add(result.length)
            insideBracket = true
        } else if (c == ']') {
            val startIndex = stack.removeAt(stack.lastIndex)
            val endIndex = result.length
            val substring = result.substring(startIndex, endIndex)
            result.delete(startIndex, endIndex)
            result.append(substring.replace(" ", ","))
            insideBracket = stack.isNotEmpty()
        }

        if (insideBracket) {
            result.append(c)
        }
        else if(!insideBracket){
            result.append(c)
        }
    }

    return result.toString()
}

fun checkNames(){
    for (name in varNames){
        if (name  !in str.split(" ")){
            varNames.remove(name)
        }
    }
    for (arr in arrNames){
        if (!str.contains("${arr}_")){
            arrNames.remove(arr)
            val forDelete = arrayListOf<String>()
            for (el in arrNamesWithIndexies){
                if ("${arr}_" in el){
                    forDelete.add(el)
                }
            }
            for (el in forDelete){
                arrNamesWithIndexies.remove(el)
            }
        }
    }

}

fun checkIndex(str : String) : String{
    if (str in variablesMap.keys){
        return variablesMap[str].toString()
    }else{
        return str
    }
}
fun replaceFigureOnDots(input: String) : String{
    val regex = Regex("\\{\\s*(.*?)\\s*\\}")
    val output = regex.replace(input) { matchResult ->
        matchResult.value.replace(" ", ",")
    }
    return  output
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
            token in varNames || "_" in token -> outputQueue.add(token) // Если токен - название переменной, добавляем его в очередь вывода
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