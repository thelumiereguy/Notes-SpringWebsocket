/*
 * Created by Piyush Pradeepkumar on 01/04/21, 11:20 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.usecases.get_notes

import com.thelumiereguy.reactivepostgres.data.notes.datasource.TestConfigForNotesDataSource
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
class GetNotesTest constructor(@Autowired val getNotes: GetNotes) {


    @Test
    fun `should return all Notes`() {
        runBlocking {
            getNotes().forEach {
                Assertions.assertThat(it).isNotNull
            }
        }
    }
}