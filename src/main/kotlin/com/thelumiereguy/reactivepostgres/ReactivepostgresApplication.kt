package com.thelumiereguy.reactivepostgres

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactivepostgresApplication

fun main(args: Array<String>) {
    runApplication<ReactivepostgresApplication>(*args)
}
