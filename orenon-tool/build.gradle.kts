plugins {
    kotlin("jvm")
    application
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.16.0")
}

application {
    mainClass.set("com.github.kitakkun.orenon.tool.MainKt")
}
