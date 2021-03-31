/*
 * Created by Piyush Pradeepkumar on 01/04/21, 1:21 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.usecases.update_note.update

import com.thelumiereguy.reactivepostgres.data.notes.datasource.INoteDataSource
import com.thelumiereguy.reactivepostgres.presentation.dto.note.Note
import com.thelumiereguy.reactivepostgres.usecases.update_note.create.BadRequestException
import com.thelumiereguy.reactivepostgres.usecases.update_note.delete.NoSuchNoteException
import com.thelumiereguy.reactivepostgres.utils.assertNotNull
import com.thelumiereguy.reactivepostgres.utils.assertNotNullOrBlank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UpdateNote constructor(@Autowired private val dataSource: INoteDataSource) {

    suspend operator fun invoke(note: Note): Note {
        note.created_by.assertNotNullOrBlank(BadRequestException("created_by"))
        note.created_on.assertNotNull(BadRequestException("created_on"))
        dataSource.getNoteById(note.id) ?: throw NoSuchNoteException("No such note exists")
        return dataSource.updateNote(note)
    }
}
