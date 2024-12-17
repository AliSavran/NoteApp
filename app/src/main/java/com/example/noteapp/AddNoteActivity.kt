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
import com.example.noteapp.databinding.ActivityAddNoteBinding


class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private val noteViewModel : NoteViewModel by viewModels {

        val database = NoteDatabase.getDatabase(application)
        val repository = NoteRepository(database.noteDao())
        val notificationPreferences = NotificationPreferences(this)
        val notificationHelper = NotificationHelper(this, notificationPreferences)
        NoteViewModel.NoteViewModelFactory(repository, notificationHelper)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSaveButton()
        setupBackButton()

    }

    private fun setupSaveButton(){
        binding.buttonSave.setOnClickListener {
            val title = binding.editTextTitle.text.toString().trim()
            val content = binding.editTextContent.text.toString().trim()

            if (title.isNotEmpty() && content.isNotEmpty()){
                val newNote = Note(title = title, content = content)
                noteViewModel.insert(newNote)
                Toast.makeText(this,"Notunuz Kaydedildi...", Toast.LENGTH_LONG).show()
                finish()
            }else{
                Toast.makeText(this,"Notunuz Kaydedilemedi...", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupBackButton(){
        binding.buttonBack.setOnClickListener {
            onBackPressed()
        }
    }
}