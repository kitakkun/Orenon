package com.github.kitakkun.orenon

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AstPrinterTest {
    @Test
    fun test() {
        val astPrinter = AstPrinter()
        val result = astPrinter.print(
            Expr.Binary(
                operator = Token(TokenType.MINUS, "-", null, 1),
                left = Expr.Literal(123),
                right = Expr.Grouping(
                    expression = Expr.Literal(45),
                ),
            )
        )
        assertEquals("(- 123 (group 45))", result)
    }
}
