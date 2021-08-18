import java.io.InputStream

class PhoneCode {
    private lateinit var dictionary: String

    fun findEncodings(phoneNumber: String): List<String> {
        return if (phoneNumber.isNotEmpty() && dictionary.isNotEmpty()) {
            listOf(dictionary)
        } else {
            emptyList()
        }
    }

    fun setDictionary(inputStream: InputStream) {
        dictionary = inputStream.readAllBytes().decodeToString()
    }
}