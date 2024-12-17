package com.example.noteapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.Adapter.NoteAdapter
import com.example.noteapp.Database.NoteDatabase
import com.example.noteapp.Model.Note
import com.example.noteapp.Repository.NoteRepository
import com.example.noteapp.Utils.NotificationHelper
import com.example.noteapp.Utils.NotificationPreferences
import com.example.noteapp.ViewModel.NoteViewModel
import com.example.noteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NoteAdapter
    private val noteViewModel : NoteViewModel by viewModels {

        val database = NoteDatabase.getDatabase(application)
        val repository = NoteRepository(database.noteDao())
        val notificationPreferences = NotificationPreferences(this)
        val notificationHelper = NotificationHelper(this, notificationPreferences)
        NoteViewModel.NoteViewModelFactory(repository, notificationHelper)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setupRecyclerView()
        setupSearchView()
        setupFab()
        observeNotes()
    }

    private fun setupRecyclerView(){
        noteAdapter = NoteAdapter(
            onEditClick = { note -> navigateToEditNote(note) },
            onDeleteClick = { note -> deleteNote(note) }
        )
        binding.recyclerViewNotes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchNotes(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchNotes(it)
                }
                return true
            }
        })
    }

    private fun setupFab(){
        binding.fabAddNote.setOnClickListener {
            navigateToAddNote()
        }
    }

    private fun observeNotes(){

        noteViewModel.allNotes.observe(this) { notes ->
            noteAdapter.submitList(notes)
        }
    }

    private fun navigateToAddNote(){
        val intent = Intent(this,AddNoteActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToEditNote(note: Note){
        val intent = Intent(this,EditNoteActivity::class.java).apply {
            putExtra("NOTE_ID", note.id)
            putExtra("NOTE_TITLE", note.title)
            putExtra("NOTE_CONTENT", note.content)
        }
        startActivity(intent)
    }

    private fun deleteNote(note: Note){
        noteViewModel.delete(note)
    }

    private fun searchNotes(query: String) {
        noteViewModel.searchNotes(query).observe(this) { notes ->
            noteAdapter.submitList(notes)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_settings -> {
                val intent= Intent(this,SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}