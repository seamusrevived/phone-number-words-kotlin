import java.io.InputStream

typealias WordSequence = List<Words>
typealias Words = List<String>
typealias FlatSequence = List<String>
typealias MutableWordSequence = MutableList<Words>


class PhoneCode {
    private val dictionaryEncodings: HashMap<String, MutableList<String>> = hashMapOf()

    fun findEncodings(phoneNumber: String): List<String> {
        val foundSequences = buildWordSequences(phoneNumber)

        return foundSequences.map {
            generateOutputStringsForSequence(it)
        }.flatten()
    }

    private fun generateOutputStringsForSequence(sequence: WordSequence): List<String> {
        return findAllFlatCombinationsForSequence(sequence).map { flatWords ->
            flatWords.joinToString(" ")
        }.filter(String::isNotBlank)
    }

    private fun buildWordSequences(
        phoneNumber: CharSequence,
        runningSequence: MutableWordSequence = mutableListOf()
    ): List<WordSequence> {
        if (phoneNumber.isEmpty()) {
            return listOf(runningSequence)
        }

        return (1..phoneNumber.length).mapNotNull { i ->
            findSequencesWithStartingWordLength(i, phoneNumber, runningSequence)
        }.flatten()
    }

    private fun findSequencesWithStartingWordLength(
        i: Int,
        phoneNumber: CharSequence,
        runningSequence: MutableWordSequence
    ): List<WordSequence>? {
        val firstNumberSequence = phoneNumber.subSequence(0, i)
        if (!dictionaryEncodings.containsKey(firstNumberSequence)) {
            return null
        }

        val nextWordsInSequence = dictionaryEncodings[firstNumberSequence]!!
        val nextRunningSequence = appendWordsToWordSequence(runningSequence, nextWordsInSequence)

        return buildWordSequences(
                phoneNumber.subSequence(i, phoneNumber.length),
                nextRunningSequence
            )
    }

    private fun appendWordsToWordSequence(
        runningSequence: MutableWordSequence,
        nextWordsInSequence: MutableList<String>
    ): MutableList<Words> {
        return runningSequence.toMutableList().apply {
            add(nextWordsInSequence)
        }
    }

    private fun findAllFlatCombinationsForSequence(
        wordsSequence: WordSequence
    ): List<FlatSequence> {
        var sequenceCombinations = listOf(emptyFlatSequence())

        wordsSequence.forEach { wordsForPosition ->
            val newCombinations = generateAllFlatCombinationsWithWords(wordsForPosition, sequenceCombinations)
            sequenceCombinations = newCombinations
        }
        return sequenceCombinations
    }

    private fun generateAllFlatCombinationsWithWords(
        wordsForPosition: Words,
        existingCombinations: List<FlatSequence>
    ): List<FlatSequence> {
        val newCombinations = mutableListOf<FlatSequence>()
        wordsForPosition.forEach { word ->
            appendWordToEachFlatSequence(word, existingCombinations, newCombinations)
        }
        return newCombinations
    }

    private fun appendWordToEachFlatSequence(
        word: String,
        flatSequences: List<FlatSequence>,
        appendedFlatSequences: MutableList<FlatSequence>
    ) {
        flatSequences.mapTo(appendedFlatSequences) { combination ->
            appendWordToFlatSequence(combination, word)
        }
    }

    private fun appendWordToFlatSequence(
        flatSequence: FlatSequence,
        word: String
    ): List<String> {
        return flatSequence.toMutableList().apply {
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
