// This code is automatically generated by program. DO NOT EDIT.
package com.github.kitakkun.orenon

import kotlin.Any

public sealed class Expr {
    public abstract fun <R> accept(visitor: Expr.Visitor<R>): R

    public data class Binary(
        public val left: Expr,
        public val right: Expr,
        public val `operator`: Token,
    ) : Expr() {
        override fun <R> accept(visitor: Expr.Visitor<R>): R = visitor.visitBinaryExpr(this)
    }

    public data class Grouping(
        public val expression: Expr,
    ) : Expr() {
        override fun <R> accept(visitor: Expr.Visitor<R>): R = visitor.visitGroupingExpr(this)
    }

    public data class Literal(
        public val `value`: Any,
    ) : Expr() {
        override fun <R> accept(visitor: Expr.Visitor<R>): R = visitor.visitLiteralExpr(this)
    }

    public data class Unary(
        public val `operator`: Token,
        public val right: Expr,
    ) : Expr() {
        override fun <R> accept(visitor: Expr.Visitor<R>): R = visitor.visitUnaryExpr(this)
    }

    public interface Visitor<R> {
        public fun visitBinaryExpr(expr: Expr.Binary): R

        public fun visitGroupingExpr(expr: Expr.Grouping): R

        public fun visitLiteralExpr(expr: Expr.Literal): R

        public fun visitUnaryExpr(expr: Expr.Unary): R
    }
}
