package com.example.noteapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.noteapp.Database.NoteDatabase
import com.example.noteapp.Model.Note
import com.example.noteapp.Repository.NoteRepository
import com.example.noteapp.Utils.NotificationHelper
import com.example.noteapp.Utils.NotificationPreferences
import com.example.noteapp.ViewModel.NoteViewModel
import com.example.noteapp.databinding.ActivityEditNoteBinding

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding
    private val noteViewModel : NoteViewModel by viewModels {

        val database = NoteDatabase.getDatabase(application)
        val repository = NoteRepository(database.noteDao())
        val notificationPreferences = NotificationPreferences(this)
        val notificationHelper = NotificationHelper(this, notificationPreferences)
        NoteViewModel.NoteViewModelFactory(repository, notificationHelper)
    }
    private var noteId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSaveButton()
        setupBackButton()
        getNoteDetails()

    }

    private fun getNoteDetails(){
        noteId = intent.getIntExtra("NOTE_ID",-1)
        val noteTitle = intent.getStringExtra("NOTE_TITLE")
        val noteContent = intent.getStringExtra("NOTE_CONTENT")

        binding.editTextTitle.setText(noteTitle)
        binding.editTextContent.setText(noteContent)
    }

    private fun setupSaveButton(){
        binding.buttonSave.setOnClickListener{
            val title = binding.editTextTitle.text.toString().trim()
            val content = binding.editTextContent.text.toString().trim()

            if (title.isNotEmpty() && content.isNotEmpty() && noteId != -1){
                val updateNote = Note(id = noteId, title = title, content=content)
                noteViewModel.update(updateNote)
                Toast.makeText(this,"Notunuz Güncellendi...",Toast.LENGTH_LONG).show()
                finish()
            }else{
                Toast.makeText(this,"Notunuz Güncellenemedi...",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupBackButton(){
        binding.buttonBack.setOnClickListener{
            onBackPressed()
        }
    }
}