import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.dokka.gradle.DokkaTask

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
        classpath("com.github.jengelman.gradle.plugins:shadow:5.2.0")
    }
}

plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.serialization") version "1.3.70"
    eclipse
    idea
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("org.jetbrains.dokka") version "0.10.1"
}

group = "xyz.rieproject"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.12.0")
    implementation("org.apache.logging.log4j:log4j-api:2.12.0")
    implementation("org.apache.logging.log4j:log4j-core:2.12.0")
    implementation("net.dv8tion:JDA:4.1.1_136")

    // Util library
    implementation("com.jagrosh:jda-utilities:3.0.3")

    // Mongodb Sync
    implementation("org.mongodb:mongodb-driver-sync:4.0.3")
    implementation("org.litote.kmongo:kmongo:4.0.1")

    implementation("org.apache.commons:commons-lang3:3.6")
    implementation("org.reflections:reflections:0.9.10")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("rieproject_deploy")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "xyz.rieproject.rieproject.NeoClusterSharding"))
        }
    }
    build {
        dependsOn(shadowJar)
    }
    val dokka by getting(DokkaTask::class) {
        outputFormat = "html"
        outputDirectory = "$buildDir/dokka"
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}