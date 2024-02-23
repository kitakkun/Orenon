// This code is automatically generated by program. DO NOT EDIT.
package com.github.kitakkun.orenon

import kotlin.Any

public sealed class Expr {
  public data class Binary(
    public val left: Expr,
    public val right: Expr,
    public val `operator`: Token,
  ) : Expr()

  public data class Grouping(
    public val expression: Expr,
  ) : Expr()

  public data class Literal(
    public val `value`: Any,
  ) : Expr()

  public data class Unary(
    public val `operator`: Token,
    public val right: Expr,
  ) : Expr()
}