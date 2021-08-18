import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PhoneCodeTest {
    @Test
    fun `Empty phone number string returns empty word list`() {
        val phoneCode = PhoneCode()

        val encodings = phoneCode.findEncodings("")

        assertEquals(emptyList<String>(), encodings)
    }
}