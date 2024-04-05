import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("java")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.grammarkit)
    alias(libs.plugins.gradleIntelliJPlugin)
}

// Disable all Gradle Tasks for the gradle-intellij-plugin as we only use the plugin for the dependencies
tasks {
    buildPlugin { enabled = false }
    buildSearchableOptions { enabled = false }
    downloadRobotServerPlugin { enabled = false }
    jarSearchableOptions { enabled = false }
    patchPluginXml { enabled = false }
    prepareSandbox { enabled = false }
    prepareTestingSandbox { enabled = false }
    prepareUiTestingSandbox { enabled = false }
    publishPlugin { enabled = false }
    runIde { enabled = false }
    runIdeForUiTests { enabled = false }
    runPluginVerifier { enabled = false }
    signPlugin { enabled = false }
    verifyPlugin { enabled = false }
    test { enabled = true }
}

intellij {
    version.set(providers.gradleProperty("ideaVersion").get())
}

sourceSets {
    main {
        java.srcDirs("src/main/kotlin", "gen")
        // resources.srcDirs "src/main/resources" // specifying the default causes a problem with processResources on Gradle 7
    }
    test {
        java.srcDirs("src/test/kotlin")
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

tasks.withType<KotlinCompile>().configureEach {
    dependsOn(generateSyntaxLexer, generateSyntaxParser)
}
