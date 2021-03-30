/*
 * Created by Piyush Pradeepkumar on 30/03/21, 10:52 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.utils

import java.lang.Exception

fun <T> T?.assertNotNull(exception: Exception) {
    if (this == null) {
        throw exception
    }
}

fun <T> T?.assertNotNullOrBlank(exception: Exception) {
    if (this is String) {
        if (this.isBlank()) {
            throw exception
        }
    }
    if (this == null) {
        throw exception
    }
}