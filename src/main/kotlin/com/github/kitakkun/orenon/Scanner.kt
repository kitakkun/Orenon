package com.github.kitakkun.orenon

class Scanner(private val source: String) {
    private val tokens = mutableListOf<Token>()
    private var start = 0
    private var current = 0
    private var line = 1
    private val isAtEnd get() = current >= source.length

    fun scanTokens(): List<Token> {
        while (!isAtEnd) {
            start = current
            scanToken()
        }

        tokens.add(Token(TokenType.EOF, "", null, line))
        return tokens
    }

    private fun scanToken() {
        when (advance()) {
            '(' -> addToken(TokenType.LEFT_PAREN)
            ')' -> addToken(TokenType.RIGHT_PAREN)
            '{' -> addToken(TokenType.LEFT_BRACE)
            '}' -> addToken(TokenType.RIGHT_BRACE)
            ',' -> addToken(TokenType.COMMA)
            '.' -> addToken(TokenType.DOT)
            '-' -> addToken(if (match('=')) TokenType.MINUS_EQUAL else TokenType.MINUS)
            '+' -> addToken(if (match('=')) TokenType.PLUS_EQUAL else TokenType.PLUS)
            '*' -> addToken(if (match('=')) TokenType.STAR_EQUAL else TokenType.STAR)
            '!' -> addToken(if (match('=')) TokenType.BANG_EQUAL else TokenType.BANG)
            '>' -> addToken(if (match('=')) TokenType.GREATER_EQUAL else TokenType.GREATER)
            '<' -> addToken(if (match('=')) TokenType.LESS_EQUAL else TokenType.LESS)
            '=' -> addToken(if (match('=')) TokenType.EQUAL_EQUAL else TokenType.EQUAL)
            ';' -> addToken(TokenType.SEMICOLON)
            '/' -> {
                if (match('/')) {
                    // skip comments
                    while (peek() != '\n' && !isAtEnd) advance()
                } else {
                    addToken(if (match('=')) TokenType.SLASH_EQUAL else TokenType.SLASH)
                }
            }

            ' ', '\r', '\t' -> {
                // ignore whitespace
            }

            '\n' -> line++
            else -> Orenon.error(line, "Unexpected character")
        }
    }

    private fun advance() = source[current++]

    private fun peek(): Char {
        if (isAtEnd) return '\u0000'
        return source[current]
    }

    private fun addToken(type: TokenType, literal: Any? = null) {
        val text = source.substring(start, current)
        tokens.add(Token(type, text, literal, line))
    }

    private fun match(expected: Char): Boolean {
        if (isAtEnd) return false
        if (source[current] != expected) return false

        current++
        return true
    }
}
