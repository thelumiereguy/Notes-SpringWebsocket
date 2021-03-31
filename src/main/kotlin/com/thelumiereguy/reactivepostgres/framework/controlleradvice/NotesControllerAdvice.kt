/*
 * Created by Piyush Pradeepkumar on 30/03/21, 11:20 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.controlleradvice

import com.thelumiereguy.reactivepostgres.framework.controllers.NotesController
import com.thelumiereguy.reactivepostgres.usecases.update_note.create.BadRequestException
import com.thelumiereguy.reactivepostgres.usecases.update_note.delete.NoSuchNoteException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice(assignableTypes = [NotesController::class])
class NotesControllerAdvice {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(exception: BadRequestException) =
        ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(NoSuchNoteException::class)
    fun handleNoNoteException(exception: NoSuchNoteException) =
        ResponseEntity(exception.message, HttpStatus.NOT_FOUND)

}