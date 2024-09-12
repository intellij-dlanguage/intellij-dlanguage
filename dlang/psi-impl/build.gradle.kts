
import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import java.nio.file.Files

plugins {
    id("java")
    id("org.gradle.idea")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.gradleIntelliJModule)
    alias(libs.plugins.grammarkit)
    alias(libs.plugins.kover)
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

val generateSyntaxLexer = tasks.register<GenerateLexerTask>("generateSyntaxLexer") {
    // source flex file
    sourceFile.set(file("src/main/jflex/io/github/intellij/dlanguage/lexer/DLanguageLexer.flex"))

    // target directory for lexer
    targetOutputDir.set(file("gen/io/github/intellij/dlanguage/"))
}


val ensureDirectory = tasks.register("ensureDirectory") {
    val file = file("gen/io/github/intellij/dlanguage/psi/impl")
    outputs.dir("gen/io/github/intellij/dlanguage/psi/impl")
    doFirst {
        Files.createDirectories(file.toPath())
    }
}

val generatePsi = tasks.register<Exec>("generatePsi") {
    dependsOn(ensureDirectory)
    workingDir("gen/io/github/intellij/dlanguage/psi/")
    executable("rdmd")
    args("${rootProject.projectDir}/scripts/types_regen_script.d", "Implementation")
}

val generate by tasks.registering {
    outputs.dirs("gen")
    dependsOn(
        generateSyntaxLexer,
        generatePsi,
    )
}

sourceSets {
    main {
        java.srcDirs("src/main/java", "src/main/kotlin", generate , "src/main/jflex")
    }
    test {
        java.srcDirs("src/test/java", "src/test/kotlin")
    }
}

tasks.clean {
    delete(generate)
}

dependencies {
    api (project(":dlang:psi-api"))
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
