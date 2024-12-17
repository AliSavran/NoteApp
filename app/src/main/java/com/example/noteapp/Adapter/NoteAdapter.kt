package com.example.noteapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.Model.Note
import com.example.noteapp.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.Locale

class NoteAdapter(private val onEditClick: (Note) -> Unit, private val onDeleteClick: (Note) -> Unit)
    : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    class NoteViewHolder(
        private val binding: ItemNoteBinding,
        private val onEditClick: (Note) -> Unit,
        private val onDeleteClick: (Note) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.apply {
                textViewTitle.text = note.title
                textViewContent.text = note.content

                // Tarih formatını düzenleme
                val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                textViewDate.text = dateFormat.format(note.createdAt)

                // Edit ve Delete butonlarına tıklama işlevselliği ekleme
                buttonEdit.setOnClickListener { onEditClick(note) }
                buttonDelete.setOnClickListener { onDeleteClick(note) }
            }
        }
    }

    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding, onEditClick, onDeleteClick)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}