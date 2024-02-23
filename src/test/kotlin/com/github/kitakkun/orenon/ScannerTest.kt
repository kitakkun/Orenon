package com.github.kitakkun.orenon

import kotlin.test.Test
import kotlin.test.assertEquals

class ScannerTest {
    @Test
    fun test() {
        val scanner = Scanner("// comment\n")
        val tokens = scanner.scanTokens()
        assertEquals(1, tokens.size)
        assertEquals(TokenType.EOF, tokens[0].type)
    }

    @Test
    fun test2() {
        val scanner = Scanner("(())   {       }")
        val tokens = scanner.scanTokens()
        assertEquals(7, tokens.size)
        assertEquals(TokenType.LEFT_PAREN, tokens[0].type)
        assertEquals(TokenType.LEFT_PAREN, tokens[1].type)
        assertEquals(TokenType.RIGHT_PAREN, tokens[2].type)
        assertEquals(TokenType.RIGHT_PAREN, tokens[3].type)
        assertEquals(TokenType.LEFT_BRACE, tokens[4].type)
        assertEquals(TokenType.RIGHT_BRACE, tokens[5].type)
        assertEquals(TokenType.EOF, tokens[6].type)
    }

    @Test
    fun stringLiteral() {
        val scanner = Scanner("\"Hello, World!\"")
        val tokens = scanner.scanTokens()
        assertEquals(2, tokens.size)
        assertEquals(TokenType.STRING, tokens[0].type)
        assertEquals("Hello, World!", tokens[0].literal)
        assertEquals(TokenType.EOF, tokens[1].type)
    }

    @Test
    fun numberLiteral() {
        val scanner = Scanner("123.55")
        val tokens = scanner.scanTokens()
        assertEquals(2, tokens.size)
        assertEquals(TokenType.NUMBER, tokens[0].type)
        assertEquals(123.55, tokens[0].literal)
        assertEquals(TokenType.EOF, tokens[1].type)
    }

    @Test
    fun identifier() {
        val scanner = Scanner("identifier")
        val tokens = scanner.scanTokens()
        assertEquals(2, tokens.size)
        assertEquals(TokenType.IDENTIFIER, tokens[0].type)
        assertEquals("identifier", tokens[0].lexeme)
        assertEquals(TokenType.EOF, tokens[1].type)
    }

    @Test
    fun keyword() {
        val scanner = Scanner("var")
        val tokens = scanner.scanTokens()
        assertEquals(2, tokens.size)
        assertEquals(TokenType.VAR, tokens[0].type)
        assertEquals("var", tokens[0].lexeme)
        assertEquals(TokenType.EOF, tokens[1].type)
    }

    @Test
    fun multilineComment() {
        val scanner = Scanner("/* multi\nline\ncomment*/")
        val tokens = scanner.scanTokens()
        assertEquals(1, tokens.size)
        assertEquals(TokenType.EOF, tokens[0].type)
    }
}
