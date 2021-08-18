import java.io.InputStream

class PhoneCode {
    private val dictionaryMap: HashMap<String, MutableList<String>> = hashMapOf()


    fun findEncodings(phoneNumber: String): List<String> {
        val foundSequences = mutableListOf<List<List<String>>>()
        buildWordSequence(phoneNumber, foundSequences)

        return foundSequences.map { sequence ->
            findAllCombinationsForSequence(sequence)
                .map { words ->
                    words.joinToString(" ")
                }.filter(String::isNotBlank)
        }.flatten().distinct()
    }

    private fun buildWordSequence(
        phoneNumber: CharSequence,
        foundSequences: MutableList<List<List<String>>>,
        runningSequence: MutableList<List<String>> = mutableListOf()
    ) {
        for (i in 1..phoneNumber.length) {
            val firstNumberSequence = phoneNumber.subSequence(0, i)
            val match = dictionaryMap[firstNumberSequence]
            if (match != null) {
                runningSequence.add(match)
                buildWordSequence(phoneNumber.subSequence(i, phoneNumber.length), foundSequences, runningSequence)
                foundSequences.add(runningSequence)
            }
        }
    }

    private fun findAllCombinationsForSequence(
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
        val dictionary = inputStream.readAllBytes()
            .decodeToString()
            .split('\n')
            .filter(String::isNotBlank)

        val characterMapping = hashMapOf(
            Pair('a', '2'),
            Pair('b', '2'),
            Pair('d', '3'),
            Pair('g', '4'),
        )


        dictionary.forEach { word ->
            val key = word.toCharArray().map {
                characterMapping[it]
            }.joinToString("")
            if (dictionaryMap[key] != null) {
                dictionaryMap[key]!!.add(word)
            } else {
                dictionaryMap[key] = mutableListOf(word)
            }
        }
    }
}