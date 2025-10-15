plugins {
    kotlin("jvm") version "2.0.0"
    application
}

group = "org.styl.gravitus"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("log4j:log4j:1.2.17")
    implementation("commons-io:commons-io:2.16.1")
    implementation("commons-io:commons-io:2.16.1")
    implementation("com.google.code.gson:gson:2.11.0")


    testImplementation(kotlin("test"))
}

application {
    mainClass.set("org.styl.gravitus.Initialiser.main")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
