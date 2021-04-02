/*
 * Created by Piyush Pradeepkumar on 01/04/21, 11:18 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.usecases.update_note.create

import com.thelumiereguy.reactivepostgres.data.notes.datasource.TestConfigForNotesDataSource
import com.thelumiereguy.reactivepostgres.presentation.dto.note.Note
import com.thelumiereguy.reactivepostgres.usecases.get_notes.GetNotes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [TestConfigForNotesDataSource::class]
)
class CreateNoteTest constructor(@Autowired val getNotes: GetNotes, @Autowired val createNote: CreateNote) {

    @Test
    fun `given a note, it should be added to datasource`() {
        val noteId = 10L
        val note = Note("test", "test", "testuser", System.currentTimeMillis(), noteId)
        runBlocking {
            createNote.invoke(note)
            Assertions.assertThat(getNotes().map { it.id }).contains(noteId)
        }
    }
}