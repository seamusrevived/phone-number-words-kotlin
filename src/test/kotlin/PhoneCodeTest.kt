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

    @Test
    fun `Phone number 23456789 with with dictionary of adgjmptw returns words adgjmptw`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("adgjmptw".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("23456789")

        assertEquals(listOf("adgjmptw"), encodings)
    }

    @Test
    fun `Phone number 23456789 with with dictionary of behknqux returns words behknqux`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("behknqux".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("23456789")

        assertEquals(listOf("behknqux"), encodings)
    }

    @Test
    fun `Phone number 23456789 with with dictionary of cfilorvy returns words cfilorvy`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("cfilorvy".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("23456789")

        assertEquals(listOf("cfilorvy"), encodings)
    }

    @Test
    fun `Phone number 79 with with dictionary of sz returns words sz`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("sz".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("79")

        assertEquals(listOf("sz"), encodings)
    }

    @Test
    fun `Phone number 2 with with dictionary of A returns words A`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("A".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("2")

        assertEquals(listOf("A"), encodings)
    }

    @Test
    fun `Phone number 2-() 3 with with dictionary of ad returns words ad`() {
        val phoneCode = PhoneCode()

        val inputStream = ByteArrayInputStream("ad".toByteArray())

        phoneCode.setDictionary(inputStream)

        val encodings = phoneCode.findEncodings("2-() 3")

        assertEquals(listOf("ad"), encodings)
    }
}