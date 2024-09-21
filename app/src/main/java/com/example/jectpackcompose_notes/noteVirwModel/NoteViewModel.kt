package com.example.jectpackcompose_notes.noteViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.jectpackcompose_notes.Model.Note
import com.example.jectpackcompose_notes.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    fun insert(note: Note) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }

    fun update(note: Note) {
        viewModelScope.launch {
            repository.update(note)
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }
    suspend fun getAllData() :List<Note> {
        return repository.getAllNotes()
    }
}
