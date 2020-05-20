import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

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
    eclipse
    idea
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "xyz.rieproject"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.12.0")
    implementation("org.apache.logging.log4j:log4j-api:2.12.0")
    implementation("org.apache.logging.log4j:log4j-core:2.12.0")
    implementation("net.dv8tion:JDA:4.1.1_136")

    // Util library
    implementation("com.jagrosh:jda-utilities:3.0.3")

    // HikariCP for SQL Pooling
    implementation("com.zaxxer:HikariCP:3.3.1")
    implementation("org.postgresql:postgresql:42.2.6")

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
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}