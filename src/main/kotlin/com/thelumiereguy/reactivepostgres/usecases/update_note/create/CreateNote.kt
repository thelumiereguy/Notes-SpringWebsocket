/*
 * Created by Piyush Pradeepkumar on 29/03/21, 1:32 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.usecases.update_note.create

import com.thelumiereguy.reactivepostgres.data.notes.datasource.INoteDataSource
import com.thelumiereguy.reactivepostgres.presentation.dto.note.Note
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CreateNote constructor(@Autowired private val dataSource: INoteDataSource) {

    suspend operator fun invoke(note: Note): Note {
        return dataSource.createNote(note)
    }
}