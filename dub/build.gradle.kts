import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java")
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

dependencies {
    api(project(":"))
    api(project(":dlang:psi-api"))
    implementation(project(":utils"))

    testImplementation(project(":"))
    testImplementation(project(":dlang:plugin-impl"))

    implementation (libs.gson) // used by dub parser

    testImplementation (libs.junit.engine)
    testRuntimeOnly (libs.junit.engine)

    intellijPlatform {
        intellijIdeaCommunity(providers.gradleProperty("ideaVersion").get())
        bundledPlugins(
            "com.intellij.java",
            "com.intellij.java.ide",
            "com.intellij.modules.json",
            "org.intellij.intelliLang",
            "com.intellij.copyright"
        )
        testFramework(TestFrameworkType.Platform)
    }
}
