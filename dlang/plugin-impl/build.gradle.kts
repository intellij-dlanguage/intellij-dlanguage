
fun properties(key: String) = providers.gradleProperty(key).get()

plugins {
    id("org.gradle.idea")
    id("java")
    alias(libs.plugins.gradleIntelliJPlatform)
    alias(libs.plugins.kover)
    alias(libs.plugins.coverallsJacoco)
}

coverallsJacoco.reportPath = "build/reports/kover/report.xml"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

intellijPlatform {
    buildSearchableOptions = false
    instrumentCode = true
    projectName = "intellij-dlanguage"

    pluginConfiguration {
        version = properties("pluginVersion")
        ideaVersion {
            sinceBuild = properties("pluginSinceBuild")
            untilBuild = properties("pluginUntilBuild")
        }
    }
    publishing {
        token = provider {
            System.getenv("JETBRAINS_TOKEN")
        }
        channels = listOf(properties("publishChannels"))
    }
    pluginVerification {
        ides {
            recommended()
        }
    }
}

tasks {
    runIde {
        jvmArgumentProviders += CommandLineArgumentProvider {
            listOf(
                "-Dide.show.tips.on.startup.default.value=false",
                "-Dide.debug.mode=true",
                // The package names have to be prefixed with a #
                "-Didea.log.debug.categories=#io.github.intellij.dlanguage"
                )
        }
    }
}

tasks.register<Test>("testCompilation") {
    group = "Verification"
    dependsOn(tasks.classes, tasks.testClasses)
    useJUnit {
        include("io/github/intellij/dlanguage/build/**")
    }
}

dependencies {
    intellijPlatform {
        pluginModule(implementation (project(":")))
        pluginModule(implementation (project(":utils")))
        pluginModule(implementation (project(":errorreporting")))
        pluginModule(implementation (project(":debugger")))
        pluginModule(implementation (project(":sdlang")))
        pluginModule(implementation (project(":dub")))

        intellijIdeaCommunity(properties("ideaVersion"))

        bundledPlugins(
            "com.intellij.java",
            "com.intellij.java.ide",
            "org.intellij.intelliLang",
            "com.intellij.copyright"
        )

        // If not run by CICD, add some useful dev plugins
        if (providers.environmentVariable("CI").orNull == null)
            plugin("psiViewer:${properties("psiViewerVersion")}")

        pluginVerifier()
        zipSigner()
        instrumentationTools()
    }

    // theses kover lines are here to generate a merged report of all the projects
    kover(project(":"))
    kover(project(":utils"))
    kover(project(":errorreporting"))
    kover(project(":debugger"))
    kover(project(":sdlang"))
    kover(project(":dub"))
}
