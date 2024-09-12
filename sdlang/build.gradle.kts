
import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask
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
        intellijIdeaCommunity(providers.gradleProperty("ideaVersion").get())
        instrumentationTools()
        testFramework(TestFrameworkType.Platform)
    }
}

val generateSyntaxLexer = tasks.register<GenerateLexerTask>("generateSyntaxLexer") {
    // source flex file
    sourceFile.set(file("src/main/kotlin/io/github/intellij/dlanguage/sdlang/lexer/SDLangLexer.flex"))

    // target directory for lexer
    targetOutputDir.set(file("gen/io/github/intellij/dlanguage/sdlang/lexer"))
}

val generateSyntaxParser = tasks.register<GenerateParserTask>("generateSyntaxParser") {
    sourceFile.set(file("src/main/kotlin/io/github/intellij/dlanguage/sdlang/parser/SDLangParser.bnf"))
    targetRootOutputDir.set(file("gen"))
    pathToParser.set("io/github/intellij/dlanguage/sdlang/parser/SDLangParser.java")
    pathToPsiRoot.set("io/github/intellij/dlanguage/sdlang/psi")
}

val generate by tasks.registering {
    outputs.dir("gen")
    dependsOn(generateSyntaxLexer, generateSyntaxParser)
}

sourceSets {
    main {
        java.srcDirs("src/main/kotlin", generate)
        // resources.srcDirs "src/main/resources" // specifying the default causes a problem with processResources on Gradle 7
    }
    test {
        java.srcDirs("src/test/kotlin")
    }
}

tasks.clean {
    delete(generate)
}

// Mark the generated sources as generated in intellij idea
idea {
    module {
        generatedSourceDirs = setOf(file("gen"))
    }
}

