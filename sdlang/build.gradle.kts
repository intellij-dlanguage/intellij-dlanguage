
import org.jetbrains.intellij.platform.gradle.TestFrameworkType


plugins {
    id("java")
    id("org.gradle.idea")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.grammarkit)
    alias(libs.plugins.gradleIntelliJModule)
    alias(libs.plugins.kover)
}


repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    testImplementation (libs.junit.engine)
    testRuntimeOnly (libs.junit.engine)

    intellijPlatform {
        intellijIdea(providers.gradleProperty("ideaVersion").get())
        testFramework(TestFrameworkType.Platform)
    }
}

tasks {
    generateLexer {
        // source flex file
        sourceFile = file("src/main/kotlin/io/github/intellij/dlanguage/sdlang/lexer/SDLangLexer.flex")
        // The default output directory for lexer is: "build/generated/sources/grammarkit-lexer/java/main"
        // It will automatically be marked as a source root in Intellij
        purgeOldFiles = true
        doLast {
            println("SDLang Lexer (io.github.intellij.dlanguage.sdlang.lexer._SDLangLexer.java) generated")
        }
    }

    generateParser {
        sourceFile = file("src/main/kotlin/io/github/intellij/dlanguage/sdlang/parser/SDLangParser.bnf")
        // The default output directory for parser is: "build/generated/sources/grammarkit-parser/java/main"
        // It will automatically be marked as a source root in Intellij
        pathToParser = "io/github/intellij/dlanguage/sdlang/parser/SDLangParser.java"
        pathToPsiRoot = "io/github/intellij/dlanguage/sdlang/psi"
        purgeOldFiles = true
        doLast {
            println("SDLang Parser and psi elements generated")
        }
    }
}

sourceSets {
    main {
        java.srcDirs(
            files(tasks.generateLexer.flatMap { it.targetRootOutputDir }).builtBy(tasks.generateLexer),
            files(tasks.generateParser.flatMap { it.targetRootOutputDir }).builtBy(tasks.generateParser),
        )
    }
}
