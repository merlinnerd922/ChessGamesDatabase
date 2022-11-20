import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-csv:1.9.0")
    implementation("me.liuwj.ktorm:ktorm-core:3.1.0")
    implementation("me.liuwj.ktorm:ktorm-support-mysql:3.1.0")
    implementation("mysql:mysql-connector-java:8.0.30")
    implementation("org.jooq:jooq:3.17.5")
    implementation("org.jooq:jooq-meta:3.17.5")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}