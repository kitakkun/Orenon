package com.github.kitakkun.orenon.tool

import kotlin.io.path.Path

fun main() {
    val outputDir = "../orenon-compiler/src/main/kotlin"
    val generator = AstClassGenerator()
    val fileSpec = generator.generate()
    fileSpec.writeTo(Path(outputDir))
}
