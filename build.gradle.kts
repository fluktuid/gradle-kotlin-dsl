import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.3.41"
    id("com.palantir.docker") version "0.22.1"
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

val appVersion = "1.0-SNAPSHOT"

group = "com.learning"
version = appVersion

application {
    mainClassName = "com.learning.Application"
}

tasks {
    "shadowJar"(ShadowJar::class) {
        mergeServiceFiles()
    }
    "jar"(Jar::class) {
        enabled = false
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

docker {
    name = "ashishkujoy/kotlin-dsl"
    files(tasks.get("shadowJar").outputs)
    setDockerfile(file("Dockerfile"))
    labels(mapOf("app-name" to "kotline", "app-version" to appVersion))
}
