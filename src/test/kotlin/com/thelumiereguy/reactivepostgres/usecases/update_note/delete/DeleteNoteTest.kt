/*
 * Created by Piyush Pradeepkumar on 02/04/21, 12:47 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.usecases.update_note.delete

import com.thelumiereguy.reactivepostgres.data.notes.datasource.TestConfigForNotesDataSource
import com.thelumiereguy.reactivepostgres.usecases.get_notes.GetNotes
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [TestConfigForNotesDataSource::class]
)
internal class DeleteNoteTest constructor(
    @Autowired val getNotes: GetNotes,
    @Autowired val deleteNote: DeleteNote
) {


    @Test
    fun `given a note Id, it should be deleted from the data source`(): Unit = runBlocking {
        val noteId = 1L
        //Assert that the note exists
        Assertions.assertThat(getNotes().map { it.id }).contains(noteId)

        deleteNote.invoke(noteId)
        //Assert that the note is deleted
        Assertions.assertThat(getNotes().map { it.id }).doesNotContain(noteId)
    }
}