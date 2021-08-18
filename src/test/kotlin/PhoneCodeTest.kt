import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

class PhoneCodeTest {
    @Test
    fun `Empty phone number string returns empty word list`() {
        val phoneCode = PhoneCode()

        val encodings = phoneCode.findEncodings("")

        assertEquals(emptyList<String>(), encodings)
    }

    @Test
    fun `Phone number 2 with dictionary a string returns word list of a`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("a".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("2")

        assertEquals(listOf("a"), encodings)
    }
}