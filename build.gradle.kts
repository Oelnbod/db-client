plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "1.7.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}