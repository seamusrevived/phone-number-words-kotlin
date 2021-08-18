import java.io.InputStream
import java.util.*
import kotlin.collections.HashMap

typealias WordSequence = List<Words>
typealias Words = List<String>
typealias FlatSequence = List<String>
typealias MutableWordSequence = MutableList<Words>


class PhoneCode {
    private val dictionaryEncodings: HashMap<String, MutableList<String>> = hashMapOf()

    fun setDictionary(inputStream: InputStream) {
        getDictionaryFileLines(inputStream).forEach { word ->
            addNumberEncodingAndWordToDictionaryEncodings(word)
        }
    }

    private fun addNumberEncodingAndWordToDictionaryEncodings(word: String) {
        val key = getNumberEncodingForWord(word)

        if (dictionaryEncodings.containsKey(key)) {
            dictionaryEncodings[key]!!.add(word)
        } else {
            dictionaryEncodings[key] = mutableListOf(word)
        }
    }

    fun findEncodings(phoneNumber: String): List<String> {
        val sanitizedPhoneNumber = sanitizePhoneNumber(phoneNumber)

        val foundSequences = buildWordSequences(sanitizedPhoneNumber)

        return foundSequences.map {
            generateOutputStringsForSequence(it)
        }.flatten()
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

    private fun generateOutputStringsForSequence(sequence: WordSequence): List<String> {
        return findAllFlatCombinationsForSequence(sequence).map { flatWords ->
            flatWords.joinToString(" ")
        }.filter(String::isNotBlank)
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

    private fun findAllFlatCombinationsForSequence(
        wordsSequence: WordSequence
    ): List<FlatSequence> {
        var sequenceCombinations = listOf(emptyFlatSequence())

        wordsSequence.forEach { wordsForPosition ->
            val newCombinations = appendAllFlatCombinationsWithWords(
                wordsForPosition,
                sequenceCombinations
            )
            sequenceCombinations = newCombinations
        }
        return sequenceCombinations
    }

    private fun appendAllFlatCombinationsWithWords(
        wordsForPosition: Words,
        existingCombinations: List<FlatSequence>
    ): List<FlatSequence> {
        val newCombinations = mutableListOf<FlatSequence>()
        wordsForPosition.forEach { word ->
            appendWordToEachFlatSequence(word, existingCombinations, newCombinations)
        }
        return newCombinations
    }

    companion object {
        private fun emptyFlatSequence(): FlatSequence = emptyList()

        private fun sanitizePhoneNumber(phoneNumber: String) = Regex("[0-9]")
            .findAll(phoneNumber)
            .map { it.value }
            .joinToString("")

        private fun getNumberEncodingForWord(word: String): String {
            return word.lowercase(Locale.getDefault()).toCharArray().map {
                PhoneUtil.phonePadLetterMapping[it]
            }.joinToString("")
        }

        private fun getDictionaryFileLines(inputStream: InputStream): List<String> {
            return inputStream.readAllBytes()
                .decodeToString()
                .split('\n')
                .filter(String::isNotBlank)
        }

        private fun appendWordsToWordSequence(
            runningSequence: MutableWordSequence,
            nextWordsInSequence: MutableList<String>
        ): MutableList<Words> {
            return runningSequence.toMutableList().apply {
                add(nextWordsInSequence)
            }
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
    }
}
