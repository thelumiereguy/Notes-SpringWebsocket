/*
 * Created by Piyush Pradeepkumar on 28/03/21, 8:53 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.data.notes.datasource

import com.thelumiereguy.reactivepostgres.presentation.dto.note.Note

interface INoteDataSource {
    suspend fun getAllNotes(): List<Note>

    suspend fun createNote(note: Note): Note

    suspend fun getNoteById(id: Long): Note?

    suspend fun deleteNote(noteId: Long)

    fun updateNote(updatedNote: Note): Note
}