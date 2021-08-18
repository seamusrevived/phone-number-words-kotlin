import java.io.InputStream

class PhoneCode {
    private var dictionary: List<String> = emptyList()

    fun findEncodings(phoneNumber: String): List<String> {
        val numberMapping = hashMapOf(
            Pair('2', listOf("a", "b")),
            Pair('3', listOf("d")),
            Pair('4', listOf("g")),
        )

        val wordsSequence = phoneNumber
            .toCharArray()
            .map { char ->
                dictionary.filter {
                    numberMapping[char]!!.contains(it)
                }
            }

        return findAllSequenceCombinations(wordsSequence)
            .map {
                it.joinToString(" ")
            }.filter(String::isNotBlank)
    }

    private fun findAllSequenceCombinations(
        wordsSequence: List<List<String>>
    ): List<List<String>> {
        var sequenceCombinations = listOf(emptyList<String>())

        wordsSequence.forEach { wordsForPosition ->
            val newCombinations = appendWordsToCombinations(wordsForPosition, sequenceCombinations)
            sequenceCombinations = newCombinations
        }
        return sequenceCombinations
    }

    private fun appendWordsToCombinations(
        wordsForPosition: List<String>,
        existingCombinations: List<List<String>>
    ): List<List<String>> {
        val newCombinations = mutableListOf<List<String>>()
        wordsForPosition.forEach { word ->
            addWordToCombinations(word, existingCombinations, newCombinations)
        }
        return newCombinations
    }

    private fun addWordToCombinations(
        word: String,
        combinations: List<List<String>>,
        newCombinations: MutableList<List<String>>
    ) {
        combinations.mapTo(newCombinations) { combination ->
            buildNewCombinationWithWord(combination, word)
        }
    }

    private fun buildNewCombinationWithWord(
        existingCombination: List<String>,
        word: String
    ): List<String> {
        return existingCombination.toMutableList().apply {
            add(word)
        }
    }

    fun setDictionary(inputStream: InputStream) {
        dictionary = inputStream.readAllBytes()
            .decodeToString()
            .split('\n')
            .filter(String::isNotBlank)
    }
}