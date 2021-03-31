/*
 * Created by Piyush Pradeepkumar on 29/03/21, 1:28 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.data.notes.datasource

import com.thelumiereguy.reactivepostgres.data.notes.entity.NoteEntity
import com.thelumiereguy.reactivepostgres.data.notes.mapper.NoteMapper
import com.thelumiereguy.reactivepostgres.presentation.dto.note.Note
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

class FakeNotesDataSource(private val noteMapper: NoteMapper) : INoteDataSource {

    private val notesList = mutableListOf<NoteEntity>()

    override suspend fun getAllNotes(): List<Note> {
        return notesList.map {
            noteMapper.fromEntity(it)
        }
    }


    override suspend fun createNote(note: Note): Note {
        val noteEntity = noteMapper.toEntity(note)
        notesList.add(noteEntity)
        return note
    }


    override suspend fun getNoteById(id: Long): Note? {
        val note = notesList.find { it.id == id }
        return note?.let { noteMapper.fromEntity(it) }
    }

    override suspend fun deleteNote(noteId: Long) {
        notesList.removeAll { it.id == noteId }
    }

    override fun updateNote(updatedNote: Note): Note {
        synchronized(Any()) {
            val index = notesList.map {
                it.id
            }.indexOf(updatedNote.id)

            notesList[index] = noteMapper.toEntity(updatedNote)

            return updatedNote
        }
    }
}

@TestConfiguration
class TestConfigForNotesDataSource {

    @Bean
    fun getNotesDataSource(noteMapper: NoteMapper): INoteDataSource {
        return FakeNotesDataSource(noteMapper)
    }

}
