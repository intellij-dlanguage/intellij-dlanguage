import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.gradleIntelliJModule)
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

    testImplementation(project(":"))
    testImplementation(project(":dlang:plugin-impl"))

    testImplementation (libs.junit.engine)
    testRuntimeOnly (libs.junit.engine)

    intellijPlatform {
        intellijIdea(providers.gradleProperty("ideaVersion").get())
        bundledPlugins(
            "com.intellij.java",
            "com.intellij.java.ide"
        )
        testFramework(TestFrameworkType.Platform)
    }
}
