package com.github.kitakkun.orenon

data class Token(
    val type: TokenType,
    val lexeme: String,
    val literal: Any?,
    val line: Int,
)
