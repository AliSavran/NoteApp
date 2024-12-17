package com.example.noteapp.Repository


import androidx.annotation.WorkerThread
import com.example.noteapp.Database.NoteDao
import com.example.noteapp.Model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(note: Note) {
        noteDao.insertNote(note)
    }

    @WorkerThread
    suspend fun update(note: Note) {
        noteDao.updateNote(note)
    }

    @WorkerThread
    suspend fun delete(note: Note) {
        noteDao.deleteNote(note)
    }

    fun searchNotes(query: String): Flow<List<Note>> {
        return noteDao.searchNotes(query)
    }
}