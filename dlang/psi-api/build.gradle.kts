
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import java.nio.file.Files

plugins {
    id("java")
    id("org.gradle.idea")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.gradleIntelliJModule)
    alias(libs.plugins.kover)
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

val ensureDirectory = tasks.register("ensureDirectory") {
    val file = file("gen/io/github/intellij/dlanguage/psi/")
    outputs.dir("gen/io/github/intellij/dlanguage/psi")
    doFirst {
        Files.createDirectories(file.toPath())
    }
}

val generatePsi = tasks.register<Exec>("generatePsi") {
    dependsOn(ensureDirectory)
    workingDir("gen/io/github/intellij/dlanguage/psi/")
    executable("rdmd")
    args("${rootProject.projectDir}/scripts/types_regen_script.d", "Interface")
}

val generate by tasks.registering {
    outputs.dirs("gen")
    dependsOn(
        generatePsi,
    )
}

sourceSets {
    main {
        java.srcDirs("src/main/java", "src/main/kotlin", generate, "src/main/jflex")
    }
    test {
        java.srcDirs("src/test/java", "src/test/kotlin")
    }
}

tasks.clean {
    delete(generate)
}

dependencies {
    implementation (project(":utils"))
    testImplementation (libs.junit.engine)
    testRuntimeOnly (libs.junit.engine)

    intellijPlatform {
        intellijIdeaCommunity(providers.gradleProperty("ideaVersion").get())
        instrumentationTools()
        testFramework(TestFrameworkType.Platform)
    }
}

// Mark the generated sources as generated in intellij idea
idea {
    module {
        generatedSourceDirs = setOf(file("gen"))
    }
}
