package com.resistthedevil5947.bible

class VerseInfo {
    var book: String? = null
    var chapter: String? = null
    var verse: String? = null
    var word: String? = null

    companion object {


        val BOOK_PREFIX = "Book_"
        val CHAPTER_PREFIX = "Chapter_"
        val VERSE_PREFIX = "Verse_"
        val WORD_PREFIX = "Word_"
    }
}