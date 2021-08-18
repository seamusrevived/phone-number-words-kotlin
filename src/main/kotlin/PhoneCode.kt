import java.io.InputStream

class PhoneCode {
    fun findEncodings(phoneNumber: String): List<String> {
        return if(phoneNumber.isEmpty()){
            emptyList()
        } else {
            listOf("a")
        }
    }

    fun setDictionary(mockInputStream: InputStream) {

    }
}