/*
 * Created by Piyush Pradeepkumar on 29/03/21, 1:59 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.usecases.update_note

import com.thelumiereguy.reactivepostgres.presentation.dto.note.Note

interface IUpdateNotesUseCase {
    operator fun invoke(note: Note): Note
}