import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java")
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
    testImplementation (libs.junit.engine)
    testRuntimeOnly (libs.junit.engine)
    intellijPlatform {
        intellijIdea(providers.gradleProperty("ideaVersion").get())
        testFramework(TestFrameworkType.Platform)
    }
}
