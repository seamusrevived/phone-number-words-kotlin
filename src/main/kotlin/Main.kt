fun main() {

    val phoneNumbers = listOf(
        "(435) 569-6753",
        "(967) 534-3556",
        "222",
        "232"
    )

    val inputStream = {}.javaClass.getResourceAsStream("dict.txt")

    val phoneCode = PhoneCode()
    if (inputStream != null) {
        phoneCode.setDictionary(inputStream)
    }

    phoneNumbers.forEach { number ->
        val encodings = phoneCode.findEncodings(number)
        println("Phone Number $number")
        encodings.forEach {
            println("\t$it")
        }
    }
}