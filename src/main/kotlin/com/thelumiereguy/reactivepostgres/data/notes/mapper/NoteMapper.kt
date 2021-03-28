/*
 * Created by Piyush Pradeepkumar on 28/03/21, 11:13 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.data.notes.mapper

import com.thelumiereguy.reactivepostgres.data.notes.entity.NoteEntity
import com.thelumiereguy.reactivepostgres.presentation.dto.note.Note
import org.springframework.stereotype.Component

@Component
class NoteMapper {

    fun toEntity(note: Note): NoteEntity =
        NoteEntity(note.title, note.content, note.created_by, note.created_on, note.id)

    fun fromEntity(note: NoteEntity): Note {
        assert(note.created_by != null)
        assert(note.created_on != null)
        return Note(note.title, note.content, note.created_by!!, note.created_on!!, note.id)
    }
}