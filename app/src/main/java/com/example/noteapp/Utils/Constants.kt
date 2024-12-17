package com.example.noteapp.Utils

object Constants {

    const val DATABASE_NAME = "notes_database"
    const val DATABASE_VERSION = 1

    // Not tablosu sabitleri
    const val TABLE_NOTES = "notes"
    const val COLUMN_ID = "id"
    const val COLUMN_TITLE = "title"
    const val COLUMN_CONTENT = "content"
    const val COLUMN_CREATED_AT = "created_at"

    // Intent extras için sabitler
    const val EXTRA_NOTE_ID = "NOTE_ID"
    const val EXTRA_NOTE_TITLE = "NOTE_TITLE"
    const val EXTRA_NOTE_CONTENT = "NOTE_CONTENT"

    // Tarih formatı sabiti
    const val DATE_FORMAT = "dd MMM yyyy"

    // Validation sabitleri
    const val MAX_TITLE_LENGTH = 100
    const val MAX_CONTENT_LENGTH = 5000
    const val MIN_TITLE_LENGTH = 1
}