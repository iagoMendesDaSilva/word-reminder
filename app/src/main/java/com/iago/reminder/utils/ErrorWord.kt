package com.iago.reminder.utils

import com.iago.reminder.models.*

val errorWord = Dictionary(
    word = "error",
    license = License(
        name = "CC BY-SA 3.0",
        url = "https://creativecommons.org/licenses/by-sa/3.0"
    ),
    sourceUrls = listOf("https://en.wiktionary.org/wiki/error"),
    phonetic = "/ˈɛɹə(ɹ)/",
    phonetics = listOf(
        Phonetic(
            audio = "",
            license = null,
            sourceUrl = null,
            text = "/ˈɛɹə(ɹ)/"
        ), Phonetic(
            audio = "https://api.dictionaryapi.dev/media/pronunciations/en/error-us.mp3",
            license = License(
                name = "BY-SA 3.0",
                url = "https://creativecommons.org/licenses/by-sa/3.0"
            ),
            sourceUrl = "https://commons.wikimedia.org/w/index.php?curid=857012",
            text = "/ˈɛɹɚ/"
        )
    ),
    meanings = listOf(
        Meaning(
            definitions = listOf(
                Definition(
                    definition = "The state, quality, or condition of being wrong.",
                    synonyms = listOf(), antonyms = listOf()
                ), Definition(
                    definition = "A mistake; an accidental wrong action or a false statement not made deliberately.",
                    synonyms = listOf(), antonyms = listOf()
                ), Definition(
                    definition = "Sin; transgression.", synonyms = listOf(),
                    antonyms = listOf()
                ), Definition(
                    definition = "A failure to complete a task, usually involving a premature termination.",
                    synonyms = listOf(), antonyms = listOf()
                ), Definition(
                    definition = "The difference between a measured or calculated value and a true one.",
                    synonyms = listOf(), antonyms = listOf()
                ), Definition(
                    definition = "A play which is scored as having been made incorrectly.",
                    synonyms = listOf(), antonyms = listOf()
                ), Definition(
                    definition = "One or more mistakes in a trial that could be grounds for review of the judgement.",
                    synonyms = listOf(), antonyms = listOf()
                ), Definition(
                    definition = "Any alteration in the DNA chemical structure occurring during DNA replication, recombination or repairing.",
                    synonyms = listOf(),
                    antonyms = listOf()
                )
            ),
            partOfSpeech = "noun",
            antonyms = listOf(), synonyms = listOf(
                "blooper",
                "blunder",
                "boo-boo",
                "defect",
                "fault",
                "faux pas",
                "flub",
                "fluff",
                "fumble",
                "gaffe",
                "lapse",
                "mistake",
                "slip",
                "stumble",
                "thinko",
                "wrength"
            )
        ), Meaning(
            definitions = listOf(
                Definition(
                    definition = "To function improperly due to an error, especially accompanied by error message.",
                    synonyms = listOf(),
                    antonyms = listOf()
                ), Definition(
                    definition = "To show or contain an error or fault.",
                    synonyms = listOf(),
                    antonyms = listOf()
                ), Definition(definition = "To err.", synonyms = listOf(), antonyms = listOf())
            ),
            partOfSpeech = "verb",
            antonyms = listOf(),
            synonyms = listOf("err")
        )
    ),
)