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

    @Test
    fun `Phone number 2 with with dictionary of a and d returns word list of a`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("a\nd".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("2")

        assertEquals(listOf("a"), encodings)
    }

    @Test
    fun `Phone number 3 with with dictionary of a and d returns word list of d`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("a\nd".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("3")

        assertEquals(listOf("d"), encodings)
    }

    @Test
    fun `Phone number 4 with with dictionary of g returns word list of g`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("g".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("4")

        assertEquals(listOf("g"), encodings)
    }

    @Test
    fun `Phone number 22 with with dictionary of a returns word list of sequence a a`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("a".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("22")

        assertEquals(listOf("a a"), encodings)
    }

    @Test
    fun `Phone number 22 with with dictionary of a b returns word list of sequence a a, a b, b a, b b`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("a\nb".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("22")

        assertEquals(setOf("a a", "a b", "b a", "b b"), encodings.toSet())
    }

    @Test
    fun `Phone number 22 with with dictionary of ab returns word list of sequence ab`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("ab".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("22")

        assertEquals(listOf("ab"), encodings)
    }

    @Test
    fun `Phone number 22 with with dictionary of a and ab returns word list of sequence a a, ab`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("a\nab".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("22")

        assertEquals(listOf("a a", "ab"), encodings)
    }


    @Test
    fun `Phone number 23 with with dictionary of d returns empty word list`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("d".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("23")

        assertEquals(emptyList<String>(), encodings)
    }
}