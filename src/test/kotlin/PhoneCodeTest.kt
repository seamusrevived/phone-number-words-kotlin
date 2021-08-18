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
    fun `Phone number 2 with dictionary of a returns word list of a`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("a".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("2")

        assertEquals(listOf("a"), encodings)
    }

    @Test
    fun `Phone number 2 with empty dictionary returns empty list`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream(ByteArray(0))

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("2")

        assertEquals(emptyList<String>(), encodings)
    }

    @Test
    fun `Phone number 2 with with dictionary of b returns word list of b`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("b".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("2")

        assertEquals(listOf("b"), encodings)
    }

    @Test
    fun `Phone number 2 with with dictionary of a and b returns word list of a and b`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("a\nb".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("2")

        assertEquals(listOf("a", "b"), encodings)
    }
}