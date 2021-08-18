import java.io.InputStream

typealias WordSequence = List<Words>
typealias Words = List<String>
typealias FlatSequence = List<String>
typealias MutableWordSequence = MutableList<Words>


class PhoneCode {
    private val dictionaryEncodings: HashMap<String, MutableList<String>> = hashMapOf()

    fun findEncodings(phoneNumber: String): List<String> {
        val foundSequences = buildWordSequences(phoneNumber)

        return foundSequences.map { sequence ->
            findAllCombinationsForSequence(sequence)
                .map { words ->
                    words.joinToString(" ")
                }.filter(String::isNotBlank)
        }.flatten()
    }

    private fun buildWordSequences(
        phoneNumber: CharSequence,
        runningSequence: MutableWordSequence = mutableListOf()
    ): List<WordSequence> {
        if (phoneNumber.isEmpty()) {
            return listOf(runningSequence)
        }

        return (1..phoneNumber.length).map { i ->
            findSequencesWithStartingWordLength(i, phoneNumber, runningSequence)
        }.flatten()
    }

    private fun findSequencesWithStartingWordLength(
        i: Int,
        phoneNumber: CharSequence,
        runningSequence: MutableWordSequence
    ): List<WordSequence> {
        val firstNumberSequence = phoneNumber.subSequence(0, i)


        val nextRunningSequence = runningSequence.toMutableList().apply {
            dictionaryEncodings[firstNumberSequence]?.let { add(it) }
        }

        return buildWordSequences(
            phoneNumber.subSequence(i, phoneNumber.length),
            nextRunningSequence
        )
    }

    private fun findAllCombinationsForSequence(
        wordsSequence: WordSequence
    ): List<FlatSequence> {
        var sequenceCombinations = listOf(emptyFlatSequence())

        wordsSequence.forEach { wordsForPosition ->
            val newCombinations = generateAllCombinationsWithWords(wordsForPosition, sequenceCombinations)
            sequenceCombinations = newCombinations
        }
        return sequenceCombinations
    }

    private fun generateAllCombinationsWithWords(
        wordsForPosition: Words,
        existingCombinations: List<FlatSequence>
    ): List<FlatSequence> {
        val newCombinations = mutableListOf<FlatSequence>()
        wordsForPosition.forEach { word ->
            appendWordToCombinations(word, existingCombinations, newCombinations)
        }
        return newCombinations
    }

    private fun appendWordToCombinations(
        word: String,
        combinations: List<FlatSequence>,
        newCombinations: MutableList<FlatSequence>
    ) {
        combinations.mapTo(newCombinations) { combination ->
            appendWordToCombination(combination, word)
        }
    }

    private fun appendWordToCombination(
        existingCombination: FlatSequence,
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
            if (dictionaryEncodings[key] != null) {
                dictionaryEncodings[key]!!.add(word)
            } else {
                dictionaryEncodings[key] = mutableListOf(word)
            }
        }
    }

    private fun emptyFlatSequence(): FlatSequence = emptyList()
}
