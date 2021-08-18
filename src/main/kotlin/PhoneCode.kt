import java.io.InputStream

class PhoneCode {
    private var dictionary: List<String> = emptyList()

    fun findEncodings(phoneNumber: String): List<String> {
        val numberMapping = hashMapOf(
            Pair("2", listOf("a", "b")),
            Pair("3", listOf("d")),
            Pair("4", listOf("g")),
        )

        return dictionary.filter {
            numberMapping[phoneNumber]!!.contains(it)
        }
    }

    fun setDictionary(inputStream: InputStream) {
        dictionary = inputStream.readAllBytes()
            .decodeToString()
            .split('\n')
            .filter(String::isNotBlank)
    }
}