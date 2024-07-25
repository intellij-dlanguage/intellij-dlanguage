
fun properties(key: String) = providers.gradleProperty(key).get()

plugins {
    id("org.gradle.idea")
    id("java")
    alias(libs.plugins.gradleIntelliJPlatform)
}

val javaVersion = properties("javaVersion")
val ideaVersion = properties("ideaVersion")
val pluginVersion = properties("version")

val publishChannels = listOf(properties("publishChannels"))

val baseIDE = "idea"

// cobertura.coverageFormats = ["html", "xml"] // coveralls plugin depends on xml format report
// cobertura.coverageSourceDirs = [sourceSets.main.java.srcDirs, sourceSets.main.kotlin.srcDirs, "gen"]
// cobertura.coverageEncoding = "UTF-8"
// cobertura.coverageExcludes = [ ".*uk.co.cwspencer.*" ]


repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

intellijPlatform {
    buildSearchableOptions = false
    instrumentCode = true
    projectName = project.name

    pluginConfiguration {
        id = "net.masterthought.dlanguage"
        name = "D Language"
        version = pluginVersion
        ideaVersion {
            sinceBuild = "233"
            untilBuild = "242.2.*"
        }
        vendor {
            name = "D Language"
            url = "https://intellij-dlanguage.github.io/"
        }
    }
    publishing {
        token = provider {
            System.getenv("JETBRAINS_TOKEN")
        }
        channels = publishChannels
    }
    verifyPlugin {
        ides {
            recommended()
        }
    }
}

tasks {
    runIde {
        jvmArgumentProviders += CommandLineArgumentProvider {
            listOf("-Dide.show.tips.on.startup.default.value=false")
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

        intellijIdeaCommunity(ideaVersion)

        bundledPlugins(
            "com.intellij.java",
            "com.intellij.java.ide",
            "org.intellij.intelliLang",
            "com.intellij.copyright"
        )

        pluginVerifier()
        zipSigner()
        instrumentationTools()
    }
}
