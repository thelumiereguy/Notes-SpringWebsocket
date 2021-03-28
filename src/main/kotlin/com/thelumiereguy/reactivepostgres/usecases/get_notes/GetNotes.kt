/*
 * Created by Piyush Pradeepkumar on 29/03/21, 1:15 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.usecases.get_notes

import com.thelumiereguy.reactivepostgres.data.notes.datasource.INoteDataSource
import com.thelumiereguy.reactivepostgres.presentation.dto.note.Note
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetNotes constructor(@Autowired private val dataSource: INoteDataSource) {
    suspend operator fun invoke(): List<Note> {
        return dataSource.getAllNotes()
    }
}