plugins {
    kotlin("jvm") version "2.0.20"
    alias(libs.plugins.kotlin.serialization.plugin)
}

group = "com.hyprland.settings.parser"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation(libs.slf4j.logger)
    implementation(libs.logback.classic)
    implementation(libs.kotlin.serialization)
    implementation(libs.arrow.core)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}