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
}

@TestConfiguration
class TestConfigForNotesDataSource {

    @Bean
    fun getNotesDataSource(noteMapper: NoteMapper): INoteDataSource {
        return FakeNotesDataSource(noteMapper)
    }

}
