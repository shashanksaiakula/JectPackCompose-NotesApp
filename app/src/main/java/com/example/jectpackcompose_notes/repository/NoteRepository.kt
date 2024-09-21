package com.example.jectpackcompose_notes.repository

import com.example.jectpackcompose_notes.Dao.NoteDao
import com.example.jectpackcompose_notes.Model.Note

class NoteRepository(private val noteDao: NoteDao) {
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    suspend fun getAllNotes(): List<Note> {
        return noteDao.getAllNotes()
    }
}
