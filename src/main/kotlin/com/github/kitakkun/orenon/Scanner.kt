package com.github.kitakkun.orenon

class Scanner(private val source: String) {
    companion object {
        private val keywords = mapOf(
            "class" to TokenType.CLASS,
            "super" to TokenType.SUPER,
            "this" to TokenType.THIS,
            "fun" to TokenType.FUN,
            "return" to TokenType.RETURN,
            "var" to TokenType.VAR,
            "if" to TokenType.IF,
            "else" to TokenType.ELSE,
            "true" to TokenType.TRUE,
            "false" to TokenType.FALSE,
            "for" to TokenType.FOR,
            "while" to TokenType.WHILE,
            "nil" to TokenType.NIL,
            "and" to TokenType.AND,
            "or" to TokenType.OR,
            "print" to TokenType.PRINT,
        )
    }

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
                } else if (match('*')) {
                    var blockCommentCount = 1
                    while (!isAtEnd) {
                        if (match('*') && match('/')) {
                            blockCommentCount--
                        } else if (match('/') && match('*')) {
                            blockCommentCount++
                        }

                        if (blockCommentCount == 0) {
                            break
                        }

                        advance()
                    }
                } else {
                    addToken(if (match('=')) TokenType.SLASH_EQUAL else TokenType.SLASH)
                }
            }

            ' ', '\r', '\t' -> {
                // ignore whitespace
            }

            '"' -> string()

            '\n' -> line++
            else -> {
                if (isDigit(peek())) {
                    number()
                } else if (isAlpha(peek())) {
                    identifier()
                } else {
                    Orenon.error(line, "Unexpected character")
                }
            }
        }
    }

    private fun advance() = source[current++]

    private fun peek(): Char {
        if (isAtEnd) return '\u0000'
        return source[current]
    }

    private fun peekNext(): Char = source.getOrElse(current + 1) { '\u0000' }

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

    private fun string() {
        while (peek() != '"' && !isAtEnd) {
            if (peek() == '\n') line++
            advance()
        }

        if (isAtEnd) {
            Orenon.error(line, "Unterminated string")
            return
        }

        // consume closing "
        advance()

        val value = source.substring(start + 1, current - 1)
        addToken(TokenType.STRING, value)
    }

    private fun number() {
        while (isDigit(peek())) advance()

        if (peek() == '.' && isDigit(peekNext())) {
            // consume the .
            advance()
            while (isDigit(peek())) advance()
        }

        addToken(TokenType.NUMBER, source.substring(start, current).toDouble())
    }

    private fun identifier() {
        while (isAlphaNumeric(peek())) advance()

        val text = source.substring(start, current)
        val type = keywords[text] ?: TokenType.IDENTIFIER

        addToken(type)
    }

    // kotlin has built-in isDigit function,
    // but it returns true even if the character is a full-width digit.
    // It is not suitable for this scanner.
    private fun isDigit(c: Char) = c in '0'..'9'

    private fun isAlpha(c: Char) = c in 'a'..'z' || c in 'A'..'Z' || c == '_'

    private fun isAlphaNumeric(c: Char) = isAlpha(c) || isDigit(c)
}
