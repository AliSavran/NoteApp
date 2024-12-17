package com.example.noteapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.Model.Note
import com.example.noteapp.Repository.NoteRepository
import com.example.noteapp.Utils.NotificationHelper
import kotlinx.coroutines.launch

class NoteViewModel(
    private val repository: NoteRepository,
    private val notificationHelper: NotificationHelper) : ViewModel() {

    val allNotes: LiveData<List<Note>> = repository.allNotes.asLiveData()

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }

    fun searchNotes(query: String): LiveData<List<Note>> {
        return repository.searchNotes(query).asLiveData()
    }

    class NoteViewModelFactory(private val repository: NoteRepository,private val notificationHelper: NotificationHelper )
        : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModel(repository,notificationHelper) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}