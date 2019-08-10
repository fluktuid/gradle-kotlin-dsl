import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val dockerPluginVersion = "0.22.1"

    application
    kotlin("jvm") version "1.3.41"
    id("com.palantir.docker") version dockerPluginVersion
    id("com.palantir.docker-run") version dockerPluginVersion
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

val dockerImageName = "ashishkujoy/kotlin-dsl"

docker {
    name = dockerImageName
    files(tasks["shadowJar"].outputs)
    setDockerfile(file("Dockerfile"))
    labels(mapOf("app-name" to "kotline", "app-version" to appVersion))
    noCache(true)
}

dockerRun {
    name = "kotlin-app"
    image = dockerImageName
    daemonize = false
    env(mapOf("APP_NAME" to "Kotlin-dsl", "PLUGIN_USED" to "palantir/docker"))
    volumes(mapOf(project.file("FooBarForDocker.txt") to "/FooBar.txt"))
    ports("3000:3001","4000:4001")
    clean = false
    // we are not using port in our application but port are getting mapped or not can be confirmed by
    // executing `docker container inspect kotlin-app`
}
