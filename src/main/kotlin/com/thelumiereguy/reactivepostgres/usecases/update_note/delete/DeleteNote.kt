/*
 * Created by Piyush Pradeepkumar on 29/03/21, 1:32 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.usecases.update_note.delete

import com.thelumiereguy.reactivepostgres.data.notes.datasource.INoteDataSource
import com.thelumiereguy.reactivepostgres.presentation.dto.note.Note
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DeleteNote constructor(@Autowired private val dataSource: INoteDataSource) {

    suspend operator fun invoke(noteId: Long): Note {
        val note = dataSource.getNoteById(noteId)
        dataSource.deleteNote(noteId)
        return note ?: throw NoSuchNoteException("No such note exists")
    }
}

class NoSuchNoteException(override val message: String?) : Exception()
