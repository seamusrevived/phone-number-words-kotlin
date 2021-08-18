## Phonecode in Kotlin

Library to find dial-pad words in phone numbers in the spirit of Prechelt's "phonecode" paper

[*An empirical comparison of C, C++, Java, Perl, Python, Rexx, and Tcl for a search/string-processing program*
](http://page.mi.fu-berlin.de/prechelt/Biblio/jccpprtTR.pdf)

## Use

`PhoneCode::setDictionary(File)` takes an `InputStream` of newline separated words as possible words encoded in phone numbers

`PhoneCode::findEncodings(String)` takes a `String` and returns a list of space-separated encoded word sequences

### Example

A dictionary file containing lines `hello` and `world` run with a phone number
`(435) 569-6753` will return a list with `"hello world"`

## Description

Given the following phone dial-pad convention

|1|2|3|4|5|6|7|8|9|0|
|---|---|---|---|---|---|---|---|---|---|
| |ABC|DEF|GHI|JKL|MNO|PQRS|TUV|WXYZ||

given a telephone number and a list of words, find all the possible encoded sequences of words
from the dictionary.

### Functional constraints

- Phone numbers are provided as strings which may include formatting symbols (e.g. -, spaces, parens)
- Phone number length does not matter and can be any length
- The word dictionary will be provided as a line separated file of ASCII-encoded words
- The phone number provided will be a string input to a function
- Output will be a `List` of `String`s
