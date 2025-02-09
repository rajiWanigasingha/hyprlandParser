plugins {
    kotlin("jvm") version "2.0.20"
    alias(libs.plugins.kotlin.serialization.plugin)
    id("maven-publish")
    alias(libs.plugins.dokka)
}

group = "com.hyprland.settings.parser"
version = "1.0-DEV"

repositories {
    mavenCentral()
}

dependencies {

    implementation(libs.slf4j.logger)
    implementation(libs.logback.classic)
    implementation(libs.kotlin.serialization)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "com.hyprland.settings.parser"
            artifactId = "parser"
            version = "1.0-SNAPSHOT"
        }
    }
    repositories {
        maven {
            mavenLocal()
        }
    }
}