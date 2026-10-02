import org.jetbrains.intellij.platform.gradle.TestFrameworkType

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

tasks.generateLexer {
    sourceFile = file("src/main/jflex/io/github/intellij/dlanguage/lexer/DLanguageLexer.flex")
    purgeOldFiles = true
    doLast {
        println("Dlang psi-impl Lexer (io.github.intellij.dlanguage.lexer._DlangLexer.java) generated")
    }
}

val regenOutputTargetBase = "build/generated/sources/rdmd-parser/java/main"

val generatePsiImplementation = tasks.register<Exec>("generatePsiImplementation") {
    description = "Generate PSI implementation classes using rdmd"
    outputs.dir(regenOutputTargetBase)
    workingDir(regenOutputTargetBase)
    executable("rdmd")
    args("${rootProject.projectDir}/scripts/types_regen_script.d", "Implementation")
    doLast {
        println(standardOutput)
        println("Dlang psi-impl PSI Implementation generated using rdmd")
    }
}

sourceSets {
    main {
        java.srcDirs(
            files("src/main/jflex"),
            files("src/main/java"),
            files("src/main/kotlin"), // we have Java source files in Kotlin dir?!?
            files(tasks.generateLexer.flatMap { it.targetRootOutputDir }).builtBy(tasks.generateLexer),
            files(regenOutputTargetBase).builtBy(generatePsiImplementation),
        )
    }
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
