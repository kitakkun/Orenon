package com.github.kitakkun.orenon

object Orenon {
    fun error(line: Int, message: String) {
        error("line $line: $message")
    }
}
