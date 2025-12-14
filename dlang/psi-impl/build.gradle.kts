
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

/*
 * Create's the working directory needed for the generatePsi task.
 * 'gen/io/github/intellij/dlanguage/psi'
 * Note that the types_regen_script.d script will create an 'impl' directory within this path
 * for the generated Java implementation classes.
 */
val createWorkingDirectory = tasks.register<DefaultTask>("createWorkingDirectory") {
    description = "Create required working dir for the generatePsi task"
    val file = file("gen/io/github/intellij/dlanguage/psi")
    outputs.dir("gen/io/github/intellij/dlanguage/psi")
    doFirst {
        Files.createDirectories(file.toPath())
    }
}

val generatePsi = tasks.register<Exec>("generatePsi") {
    description = "Generate PSI implementation classes using rdmd"
    dependsOn(createWorkingDirectory)
    outputs.dir("gen/io/github/intellij/dlanguage/psi/impl")
    workingDir("gen/io/github/intellij/dlanguage/psi/")
    executable("rdmd")
    args("${rootProject.projectDir}/scripts/types_regen_script.d", "Implementation")
    doLast {
        standardOutput?.let { println(it) }
    }
}

val generate by tasks.registering {
    outputs.dirs("gen")
    inputs.file("${rootProject.projectDir}/scripts/types_regen_script.d")
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
        intellijIdea(providers.gradleProperty("ideaVersion").get())
        testFramework(TestFrameworkType.Platform)
    }
}

// Mark the generated sources as generated in intellij idea
idea {
    module {
        generatedSourceDirs = setOf(file("gen"))
    }
}
