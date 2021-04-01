/*
 * Created by Piyush Pradeepkumar on 02/04/21, 12:42 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.usecases.update_note.update

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
internal class UpdateNoteTest constructor(
    @Autowired val getNotes: GetNotes,
    @Autowired val updateNote: UpdateNote
) {


    @Test
    fun `given a note Id, it should be deleted from the data source`(): Unit = runBlocking {
        val note = getNotes().first()
        val originalTitle = note.title
        val newTitle = "UpdatedTitle"

        val newNote = note.copy(title = newTitle)

        updateNote.invoke(newNote)

        //Optional assert to check that the title is indeed different
        Assertions.assertThat(originalTitle).isNotEqualTo(newTitle)

        //Assert that the note is updated
        Assertions.assertThat(getNotes().first { it.id == newNote.id }.title).isEqualTo(newTitle)
    }
}