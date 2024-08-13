import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("java")
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
