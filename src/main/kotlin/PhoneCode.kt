import java.io.InputStream

class PhoneCode {
    private var dictionary: List<String> = emptyList()

    fun findEncodings(phoneNumber: String): List<String> {
        val numberMapping = hashMapOf(
            Pair('2', listOf("a", "b")),
            Pair('3', listOf("d")),
            Pair('4', listOf("g")),
        )

        val wordsSequence: List<List<String>> = phoneNumber
            .toCharArray()
            .map { char ->
                dictionary.filter {
                    numberMapping[char]!!.contains(it)
                }
            }

        var sequenceCombinations = arrayListOf<ArrayList<String>>(arrayListOf())

        for (possibleWordsForPosition in wordsSequence) {
            val newCombinations = arrayListOf<ArrayList<String>>()
            for (singleWord in possibleWordsForPosition) {
                for(existingCombination in sequenceCombinations) {
                    val newCombination = existingCombination.clone() as ArrayList<String>
                    newCombination.add(singleWord)
                    newCombinations.add(newCombination)
                }
            }
            sequenceCombinations = newCombinations
        }

        if(sequenceCombinations.size == 0) return listOf()
        if(sequenceCombinations.first().size == 0) return listOf()

        return sequenceCombinations.map {
            it.joinToString(" ")
        }
    }

    fun setDictionary(inputStream: InputStream) {
        dictionary = inputStream.readAllBytes()
            .decodeToString()
            .split('\n')
            .filter(String::isNotBlank)
    }
}