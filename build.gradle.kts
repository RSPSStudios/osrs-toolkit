import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    `maven-publish`
    kotlin("jvm") version "1.4.31"
}

group = "com.javatar"
version = "0.1"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("com.javatar:osrs-definitions:0.1")
    implementation("com.google.guava:guava:30.1-jre")
    testImplementation("junit", "junit", "4.13.2")
    testImplementation("com.displee:rs-cache-library:6.8")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.javatar"
            artifactId = "osrs-encoders"
            version = "0.1"
            from(components["java"])
        }
    }
}
