
import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// The same as `--stacktrace` param
gradle.startParameter.showStacktrace = ShowStacktrace.ALWAYS

plugins {
    id("java")
    id("org.gradle.idea")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.gradleIntelliJModule)
    alias(libs.plugins.gradleIntelliJPlatform).apply(false) // required to prevent resolution error
    alias(libs.plugins.grammarkit)
    alias(libs.plugins.kover)
}

// When testing, set to "true" if you want to have expected data written (to easily update lexer/parser tests)
val overrideTestData = "false"

allprojects {
    tasks {
        withType<JavaCompile> {
            sourceCompatibility = JavaVersion.VERSION_21.majorVersion
            targetCompatibility = JavaVersion.VERSION_21.majorVersion
            options.encoding = "UTF-8"
            options.compilerArgs.add("-Xlint:deprecation")
        }

        withType<KotlinCompile> {
            compilerOptions {
                apiVersion.set(KotlinVersion.KOTLIN_1_9)
                languageVersion.set(KotlinVersion.KOTLIN_1_9)
                jvmTarget.set(JvmTarget.JVM_21)
                //allWarningsAsErrors.set(true)
            }
        }

        withType<Test> {
            systemProperty("idea.tests.overwrite.data", overrideTestData)
            systemProperty("java.awt.headless", "true") // avoid "Must be precomputed" error, because IDE is not started (LoadingState.APP_STARTED.isOccurred is false)
        }
    }

}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

tasks.register<Test>("testCompilation") {
    group = "Verification"
    dependsOn(tasks.classes, tasks.testClasses)
    useJUnit {
        include("io/github/intellij/dlanguage/build/**")
    }
}

val generateDocSyntaxLexer = tasks.register<GenerateLexerTask>("generateDocSyntaxLexer") {
    // source flex file
    sourceFile.set(file("src/main/kotlin/io/github/intellij/dlanguage/features/documentation/DDocLexer.flex"))

    // target directory for lexer
    targetOutputDir.set(file("gen/io/github/intellij/dlanguage/features/documentation/"))
}

val generate by tasks.registering {
    outputs.dir("gen")
    dependsOn(generateDocSyntaxLexer)
}


sourceSets {
    main {
        java.srcDirs("src/main/java", "src/main/kotlin", generate, "src/main/jflex")
        // resources.srcDirs("src/main/resources") // specifying the default causes a problem with processResources on Gradle 7
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
    implementation (project(":errorreporting"))
    implementation (project(":debugger"))
    implementation (project(":sdlang"))
    implementation (project(":dlang:psi-impl"))
    testImplementation (project(":dlang:plugin-impl"))

    implementation (libs.gson) // used by dub parser

    testImplementation (libs.mockito.kotlin)

    testImplementation (libs.junit.engine)
    testRuntimeOnly (libs.junit.engine)

    intellijPlatform {
        intellijIdeaCommunity(providers.gradleProperty("ideaVersion").get())
        bundledPlugins(
            "com.intellij.java",
            "com.intellij.java.ide",
            "org.intellij.intelliLang",
            "com.intellij.copyright"
        )
        instrumentationTools()
        testFramework(TestFrameworkType.Plugin.Java)
    }
}

// Mark the generated sources as generated in intellij idea
idea {
    module {
        generatedSourceDirs = setOf(file("gen"))
    }
}
