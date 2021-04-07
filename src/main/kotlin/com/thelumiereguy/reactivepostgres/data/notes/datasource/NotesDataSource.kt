/*
 * Created by Piyush Pradeepkumar on 29/03/21, 12:40 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.data.notes.datasource

import com.thelumiereguy.reactivepostgres.data.notes.entity.NoteEntity
import com.thelumiereguy.reactivepostgres.data.notes.mapper.NoteMapper
import com.thelumiereguy.reactivepostgres.presentation.dto.note.Note
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class NotesDataSource constructor(@Autowired val noteMapper: NoteMapper) : INoteDataSource {

    private val notesList = mutableListOf<NoteEntity>()

    init {
        println("NotesDataSource")
        notesList.add(
            NoteEntity(
                "Note 1",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "thelumiereguy",
                System.currentTimeMillis(),
                1
            )
        )

        notesList.add(
            NoteEntity(
                "Note 2",
                "Contrary to popular belief, Lorem Ipsum is not simply random text.",
                "Probablythelumiereguy",
                System.currentTimeMillis(),
                2
            )
        )

        notesList.add(
            NoteEntity(
                "Note 3",
                "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.",
                "Notthelumiereguy",
                System.currentTimeMillis(),
                3
            )
        )
    }

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
        synchronized(Any()) {
            notesList.removeAll { it.id == noteId }
        }
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

@Configuration
class ConfigForNotesDataSource {

    @Bean
    fun getNotesDataSource(noteMapper: NoteMapper): INoteDataSource {
        return NotesDataSource(noteMapper)
    }

}