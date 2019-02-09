package state

import arrow.effects.IO

fun ask(message: String) = puts(message).flatMap { reads() }

fun puts(message: String): IO<Unit> = IO { println(message) }

fun reads(): IO<String> = IO.just(readLine()!!)

fun printError(e: Throwable) = puts("error " + e.toString())
