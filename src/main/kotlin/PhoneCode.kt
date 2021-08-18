import java.io.InputStream
import java.util.*
import kotlin.collections.HashMap

typealias PartitioningSequence = List<PartitionWords>
typealias PartitionWords = List<String>
typealias FlatWordSequence = List<String>
typealias MutablePartitioningSequence = MutableList<PartitionWords>


class PhoneCode {
    private val dictionaryEncodings: HashMap<String, MutableList<String>> = hashMapOf()

    fun setDictionary(inputStream: InputStream) {
        getDictionaryFileLines(inputStream).forEach { word ->
            addNumberEncodedWordToDictionaryEncodings(word.trimEnd())
        }
    }

    private fun addNumberEncodedWordToDictionaryEncodings(word: String) {
        val key = getNumberEncodingForWord(word)

        if (dictionaryEncodings.containsKey(key)) {
            dictionaryEncodings[key]!!.add(word)
        } else {
            dictionaryEncodings[key] = mutableListOf(word)
        }
    }

    fun findEncodings(phoneNumber: String): List<String> {
        val sanitizedPhoneNumber = sanitizePhoneNumber(phoneNumber)

        val foundPartitionings = buildWordPartitionings(sanitizedPhoneNumber)

        return foundPartitionings.map { partitioning ->
            generateOutputStringsForPartitioning(partitioning)
        }.flatten()
    }

    private fun buildWordPartitionings(
        phoneNumber: CharSequence,
        runningSequence: MutablePartitioningSequence = mutableListOf()
    ): List<PartitioningSequence> {
        if (phoneNumber.isEmpty()) {
            return listOf(runningSequence)
        }

        return (1..phoneNumber.length).mapNotNull { i ->
            val headNumbers = phoneNumber.subSequence(0, i)
            val tailNumbers = phoneNumber.subSequence(i, phoneNumber.length)

            findWordsPartitionedBy(
                headNumbers,
                tailNumbers,
                runningSequence
            )
        }.flatten()
    }

    private fun findWordsPartitionedBy(
        headNumbers: CharSequence,
        tailNumbers: CharSequence,
        runningSequence: MutablePartitioningSequence
    ): List<PartitioningSequence>? {
        if (!dictionaryEncodings.containsKey(headNumbers)) {
            return null
        }

        val nextWordsInSequence = dictionaryEncodings[headNumbers]!!
        val nextRunningSequence = appendWordsToWordSequence(runningSequence, nextWordsInSequence)

        return buildWordPartitionings(
            tailNumbers,
            nextRunningSequence
        )
    }

    private fun generateOutputStringsForPartitioning(sequence: PartitioningSequence): List<String> {
        return findAllFlatCombinationsForPartitioning(sequence).map { flatWords ->
            flatWords.joinToString(" ")
        }.filter(String::isNotBlank)
    }

    private fun findAllFlatCombinationsForPartitioning(
        partitioningSequence: PartitioningSequence
    ): List<FlatWordSequence> {
        var flatSequenceCombinations = listOf(emptyFlatSequence())

        partitioningSequence.forEach { wordsInPartition ->
            val newCombinations = appendAllFlatSequencesWithWords(
                wordsInPartition,
                flatSequenceCombinations
            )
            flatSequenceCombinations = newCombinations
        }
        return flatSequenceCombinations
    }

    private fun appendAllFlatSequencesWithWords(
        partitionWords: PartitionWords,
        existingFlatSequences: List<FlatWordSequence>
    ): List<FlatWordSequence> {
        val newCombinations = mutableListOf<FlatWordSequence>()
        partitionWords.forEach { word ->
            appendWordToEachFlatSequence(word, existingFlatSequences, newCombinations)
        }
        return newCombinations
    }

    companion object {
        private fun emptyFlatSequence(): FlatWordSequence = emptyList()

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
            runningSequence: MutablePartitioningSequence,
            nextWordsInSequence: MutableList<String>
        ): MutableList<PartitionWords> {
            return runningSequence.toMutableList().apply {
                add(nextWordsInSequence)
            }
        }

        private fun appendWordToEachFlatSequence(
            word: String,
            flatSequences: List<FlatWordSequence>,
            appendedFlatSequences: MutableList<FlatWordSequence>
        ) {
            flatSequences.mapTo(appendedFlatSequences) { combination ->
                appendWordToFlatSequence(combination, word)
            }
        }

        private fun appendWordToFlatSequence(
            flatSequence: FlatWordSequence,
            word: String
        ): List<String> {
            return flatSequence.toMutableList().apply {
                add(word)
            }
        }
    }
}
