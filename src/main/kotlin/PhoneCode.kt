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

        val sequenceCombinations = findAllSequenceCombinations(wordsSequence)

        if(sequenceCombinations.isEmpty()) return listOf()
        if(sequenceCombinations.first().isEmpty()) return listOf()

        return sequenceCombinations.map {
            it.joinToString(" ")
        }
    }

    private fun findAllSequenceCombinations(wordsSequence: List<List<String>>): List<List<String>> {
        var sequenceCombinations = listOf<List<String>>(emptyList())

        for (wordsForPosition in wordsSequence) {
            val newCombinations = addWordsToCombinations(wordsForPosition, sequenceCombinations)
            sequenceCombinations = newCombinations
        }
        return sequenceCombinations
    }

    private fun addWordsToCombinations(
        wordsForPosition: List<String>,
        sequenceCombinations: List<List<String>>
    ): List<List<String>> {
        val newCombinations = mutableListOf<List<String>>()
        for (word in wordsForPosition) {
            addWordToCombinations(sequenceCombinations, word, newCombinations)
        }
        return newCombinations.toList()
    }

    private fun addWordToCombinations(
        sequenceCombinations: List<List<String>>,
        word: String,
        newCombinations: MutableList<List<String>>
    ) {
        for (existingCombination in sequenceCombinations) {
            val newCombination = buildNewCombinationForWord(existingCombination, word)
            newCombinations.add(newCombination)
        }
    }

    private fun buildNewCombinationForWord(
        existingCombination: List<String>,
        word: String
    ): List<String> {
        val newCombination = existingCombination.toMutableList()
        newCombination.add(word)
        return newCombination
    }

    fun setDictionary(inputStream: InputStream) {
        dictionary = inputStream.readAllBytes()
            .decodeToString()
            .split('\n')
            .filter(String::isNotBlank)
    }
}