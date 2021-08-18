import java.io.InputStream

class PhoneCode {
    private var dictionary: Boolean = false

    fun findEncodings(phoneNumber: String): List<String> {
        return if(phoneNumber.isEmpty() || !dictionary){
            emptyList()
        } else {
            listOf("a")
        }
    }

    fun setDictionary(inputStream: InputStream) {
        dictionary = inputStream.read() > 0
    }
}