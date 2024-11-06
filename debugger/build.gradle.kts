import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.gradleIntelliJModule)
    alias(libs.plugins.kover)
}

kover {
    reports {
        filters {
            excludes {
                classes.add("uk.co.cwspencer.*")
            }
        }
    }
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    implementation (project(":utils"))
    api (project(":dlang:psi-api"))
    implementation (project(":dlang:psi-impl")) // TODO should not be needed, move used element in psi-api
    testImplementation (libs.junit.engine)
    testRuntimeOnly (libs.junit.engine)
    intellijPlatform {
        intellijIdea(providers.gradleProperty("ideaVersion").get())
        plugin("com.intellij.nativeDebug:${providers.gradleProperty("nativeDebugVersion").get()}")
        testFramework(TestFrameworkType.Platform)
    }
}
