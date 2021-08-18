import java.io.InputStream

class PhoneCode {
    private lateinit var dictionary: List<String>

    fun findEncodings(phoneNumber: String): List<String> {
        return if (phoneNumber.isNotEmpty() && dictionary.isNotEmpty()) {
            dictionary
        } else {
            emptyList()
        }
    }

    fun setDictionary(inputStream: InputStream) {
        dictionary = inputStream.readAllBytes()
            .decodeToString()
            .split('\n')
            .filter { s -> s != "" }
    }
}