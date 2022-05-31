plugins {
    java

    id("com.github.johnrengelman.shadow") version "7.0.0"
    kotlin("jvm") version "1.6.21"
}

group = "gg.dimensionalmc"
version = "1.1"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    jcenter()
//    maven("https://papermc.io/repo/repository/maven-public")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")

    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("net.kyori:adventure-platform-bukkit:4.1.0")
    implementation("club.minnced:discord-webhooks:0.8.0")
}



//tasks.withType<KotlinCompile> {
//    kotlinOptions { jvmTarget = "16" }
//}
