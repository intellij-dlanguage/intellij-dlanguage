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
    implementation(project(":utils"))
    implementation(project(":errorreporting"))
    implementation(project(":dlang:psi-api"))
    testImplementation(project(":"))
    testImplementation(project(":dlang:plugin-impl"))

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
        testFramework(TestFrameworkType.Platform)
    }
}
