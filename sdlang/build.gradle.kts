import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.grammarkit")
    id("org.jetbrains.intellij")
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
    targetDir.set("gen/io/github/intellij/dlanguage/sdlang/lexer")

    // target classname, target file will be targetDir/targetClass.java
    targetClass.set("_SDLangLexer")
}

val generateSyntaxParser = tasks.register<GenerateParserTask>("generateSyntaxParser") {
    sourceFile.set(file("src/main/kotlin/io/github/intellij/dlanguage/sdlang/parser/SDLangParser.bnf"))
    targetRoot.set("gen")
    pathToParser.set("io/github/intellij/dlanguage/sdlang/parser/SDLangParser.java")
    pathToPsiRoot.set("io/github/intellij/dlanguage/sdlang/psi")
}

tasks.withType<KotlinCompile>().configureEach {
    dependsOn(generateSyntaxLexer, generateSyntaxParser)
}
