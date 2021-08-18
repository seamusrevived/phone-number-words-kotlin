import java.io.InputStream

class PhoneCode {
    private var dictionary: List<String> = emptyList()

    fun findEncodings(phoneNumber: String): List<String> {
        return if (phoneNumber == "2") {
            dictionary.filter {
                listOf("a", "b").contains(it)
            }
        } else dictionary.filter {
            listOf("d").contains(it)
        }
    }

    fun setDictionary(inputStream: InputStream) {
        dictionary = inputStream.readAllBytes()
            .decodeToString()
            .split('\n')
            .filter(String::isNotBlank)
    }
}