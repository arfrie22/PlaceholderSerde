plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.kyori.blossom") version "1.3.1"
}

group = "com.github.arfrie22"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi")
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    implementation("org.jetbrains:annotations:24.0.0")
    compileOnly("me.clip:placeholderapi:2.11.2")
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")

    implementation("net.kyori:adventure-text-serializer-legacy:4.14.0")
    implementation("net.kyori:adventure-text-serializer-plain:4.14.0")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")
    implementation("net.kyori:adventure-text-serializer-ansi:4.14.0")
}

blossom {
    replaceToken("VERSION", version)
}