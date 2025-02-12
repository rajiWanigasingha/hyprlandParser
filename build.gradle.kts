plugins {
    kotlin("jvm") version "2.0.20"
    alias(libs.plugins.kotlin.serialization.plugin)
    id("maven-publish")
    alias(libs.plugins.dokka)
}

group = "com.hyprland.settings.parser"
version = "0.0.1-DEV"

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

tasks.dokkaHtml {
    outputDirectory.set(rootProject.layout.projectDirectory.dir("docs"))
}

tasks.dokkaGfm {
    outputDirectory.set(layout.buildDirectory.dir("documentation/markdown"))
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "com.hyprland.settings.parser"
            artifactId = "parser"
            version = "0.0.1-DEV"
        }
    }
    repositories {
        maven {
            mavenLocal()
        }
    }
}