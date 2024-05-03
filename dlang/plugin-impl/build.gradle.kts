fun properties(key: String) = providers.gradleProperty(key).get()

plugins {
    id("org.gradle.idea")
    id("java")
    alias(libs.plugins.gradleIntelliJPlugin)
}

val javaVersion = properties("javaVersion")
val ideaVersion = properties("ideaVersion")

val publishChannels = listOf(properties("publishChannels"))

val baseIDE = "idea"

// cobertura.coverageFormats = ["html", "xml"] // coveralls plugin depends on xml format report
// cobertura.coverageSourceDirs = [sourceSets.main.java.srcDirs, sourceSets.main.kotlin.srcDirs, "gen"]
// cobertura.coverageEncoding = "UTF-8"
// cobertura.coverageExcludes = [ ".*uk.co.cwspencer.*" ]

intellij {
    pluginName.set("intellij-dlanguage")
    version.set(ideaVersion)
    //type.set("CL") // maybe helpful to change this when trying other IDE. can be "IC", "IU", "JPS", or "CL"
    updateSinceUntilBuild.set(false)
    downloadSources.set(!System.getenv().containsKey("CI") && "IC" == type.get()) // Only download sources when type is Intellij (required because no public source code is available for CLion)

    val pluginList = mutableListOf(
        "org.intellij.intelliLang",
        "com.intellij.copyright"
    )

    if (baseIDE == "idea") {
        pluginList += listOf(
            "com.intellij.java",
            "com.intellij.java.ide"
        )
    }
    else if (baseIDE == "clion" || baseIDE == "appcode") {
        pluginList += listOf(
            "com.intellij.cidr.base",
            "com.intellij.cidr.lang"
        )
        if (baseIDE == "clion")
            pluginList += listOf(
                "com.intellij.clion"
            )
    }
    plugins.set(pluginList)

    // uncomment to test/run/debug the plugin with another IDE such as CLion or AppCode
    //localPath = "<path to>/CLion.app"
    //localPath = "C:\\Users\\USERNAME\\AppData\\Local\\JetBrains\\Toolbox\\apps\\CLion\\ch-0\\222.3345.126"
    //localPath = "$System.env.HOME/.local/share/JetBrains/Toolbox/apps/CLion/ch-0/222.3345.126"
    //localPath = "$System.env.HOME/.local/share/JetBrains/Toolbox/apps/Rider/ch-0/222.3739.37"

    tasks {
        buildPlugin { enabled = true }
        downloadRobotServerPlugin { enabled = true }
        jarSearchableOptions { enabled = true }
        patchPluginXml { enabled = true }
        //listProductsReleases { enabled = false }
        prepareSandbox { enabled = true }
        prepareTestingSandbox { enabled = true }
        prepareUiTestingSandbox { enabled = true }
        runIdeForUiTests { enabled = true }
        runPluginVerifier { enabled = true }
        signPlugin { enabled = true }
        verifyPlugin { enabled = true }

        buildSearchableOptions {
            // workaround for https://youtrack.jetbrains.com/issue/IDEA-210683
            jvmArgs = listOf(
                "--illegal-access=warn",
                "--add-opens=java.base/java.lang=ALL-UNNAMED",
                "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",
                "--add-opens=java.base/jdk.internal.vm=ALL-UNNAMED",
                "--add-opens=java.desktop/sun.awt=ALL-UNNAMED",
                "--add-opens=java.desktop/sun.awt.image=ALL-UNNAMED",
                "--add-opens=java.desktop/sun.awt.X11=ALL-UNNAMED", // Linux only
                "--add-opens=java.desktop/sun.font=ALL-UNNAMED",
                "--add-opens=java.desktop/sun.swing=ALL-UNNAMED",
                "--add-opens=java.desktop/java.awt=ALL-UNNAMED",
                "--add-opens=java.desktop/java.awt.peer=ALL-UNNAMED",
                "--add-opens=java.desktop/java.awt.event=ALL-UNNAMED",
                "--add-opens=java.desktop/javax.swing=ALL-UNNAMED",
                "--add-opens=java.desktop/javax.swing.text.html=ALL-UNNAMED",
                "--add-opens=java.desktop/javax.swing.plaf.basic=ALL-UNNAMED",
                "--add-opens=java.desktop/com.sun.java.swing.plaf.gtk=ALL-UNNAMED", // Linux only
                "--add-opens=java.desktop/com.apple.eawt.event=ALL-UNNAMED",  // Mac OSX only
            )
            enabled = false // workaround for https://youtrack.jetbrains.com/issue/KT-42837
        }

        runIde {
            enabled = true
            jvmArgs = listOf(
                "--illegal-access=warn",
                "--add-opens=java.base/java.lang=ALL-UNNAMED",
                "--add-opens=java.base/java.nio=ALL-UNNAMED",
                "--add-opens=java.base/java.util=ALL-UNNAMED",
                "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",
                "--add-opens=java.base/jdk.internal.vm=ALL-UNNAMED",
                "--add-opens=java.desktop/sun.awt=ALL-UNNAMED",
                "--add-opens=java.desktop/sun.awt.image=ALL-UNNAMED",
                "--add-opens=java.desktop/sun.awt.X11=ALL-UNNAMED", // Linux only
                "--add-opens=java.desktop/sun.font=ALL-UNNAMED",
                "--add-opens=java.desktop/sun.swing=ALL-UNNAMED",
                "--add-opens=java.desktop/sun.java2d=ALL-UNNAMED",
                "--add-opens=java.desktop/java.awt=ALL-UNNAMED",
                "--add-opens=java.desktop/java.awt.peer=ALL-UNNAMED",
                "--add-opens=java.desktop/java.awt.event=ALL-UNNAMED",
                "--add-opens=java.desktop/javax.swing=ALL-UNNAMED",
                "--add-opens=java.desktop/javax.swing.text.html=ALL-UNNAMED",
                "--add-opens=java.desktop/javax.swing.plaf.basic=ALL-UNNAMED",
                "--add-opens=java.desktop/com.sun.java.swing.plaf.gtk=ALL-UNNAMED", // Linux only
                "--add-opens=java.desktop/com.apple.eawt.event=ALL-UNNAMED",  // Mac OSX only
            )
            systemProperty("idea.debug.mode", "true")
            // The package names have to be prefixed with a #
            systemProperty("idea.log.debug.categories", "#io.github.intellij.dlanguage")
            //systemProperty("idea.log.trace.categories"], "#io.github.intellij.dlanguage")

            // Enable internal mode (https://plugins.jetbrains.com/docs/intellij/enabling-internal.html)
            systemProperty("idea.is.internal", "true")

            // idea.auto.reload.plugins=false
            systemProperty("ide.show.tips.on.startup.default.value", "false")

            // Set these properties to test alternative languages
            // systemProperty("user.language"], "de")
            // systemProperty("user.country"], "DE")

            // jbrVersion = null
            // jbrVersion = "11_0_4b304.69"
        }

        publishPlugin {
            enabled = true
            token.set(provider {
                System.getenv("JETBRAINS_TOKEN")
            })
            channels.set(publishChannels)
        }
    }
}


// take the version number defined in gradle build and use that in plugin.xml
tasks.register<Copy>("initConfig") {
    from("src/main/resources") {
        include("**/plugin.xml")
        expand("version" to version)
    }
}

tasks.register<Test>("testCompilation") {
    group = "Verification"
    dependsOn(tasks.classes, tasks.testClasses)
    useJUnit {
        include("io/github/intellij/dlanguage/build/**")
    }
}

//val generateSyntaxLexer = tasks.register<GenerateLexerTask>("generateSyntaxLexer") {
//    // source flex file
//    sourceFile.set(file("src/main/jflex/io/github/intellij/dlanguage/lexer/DLanguageLexer.flex"))
//
//    // target directory for lexer
//    targetDir.set("gen/io/github/intellij/dlanguage/")
//
//    // target classname, target file will be targetDir/targetClass.java
//    targetClass.set("DlangLexer")
//}
//
//val generateDocSyntaxLexer = tasks.register<GenerateLexerTask>("generateDocSyntaxLexer") {
//    // source flex file
//    sourceFile.set(file("src/main/kotlin/io/github/intellij/dlanguage/features/documentation/DDocLexer.flex"))
//
//    // target directory for lexer
//    targetDir.set("gen/io/github/intellij/dlanguage/features/documentation")
//
//    // target classname, target file will be targetDir/targetClass.java
//    targetClass.set("_DDocLexer")
//}
//
//val copyGenScript = tasks.register<Copy>("copyGenScript") {
//    from("src/main/resources/types_regen_script.d")
//    into("gen/io/github/intellij/dlanguage/psi/")
//}
//
//val generatePsi = tasks.register<Exec>("generatePsi") {
//    dependsOn(copyGenScript)
//    workingDir("gen/io/github/intellij/dlanguage/psi/")
//    commandLine("rdmd", "types_regen_script.d")
//}
//
//tasks.withType<JavaCompile>().configureEach {
//    dependsOn(
//        generateSyntaxLexer,
//        generateDocSyntaxLexer,
//        generatePsi
//    )
//}
//
//tasks.withType<KotlinCompile>().configureEach {
//    dependsOn(
//        generateSyntaxLexer,
//        generateDocSyntaxLexer,
//        generatePsi
//    )
//}

dependencies {
    // According to https://www.jetbrains.org/intellij/sdk/docs/tutorials/kotlin.html
    // we should not need to include kotlin-runtime and kotlin-stdlib jars with a plugin
    // as it's bundled with Intellij, however if we support multiple Intellij versions (which
    // we do) then it may be worth bundling our own kotlin jars
    //compileOnly "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
    //compileOnly "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"

    implementation (project(":", "instrumentedJar"))
    implementation (project(":utils", "instrumentedJar"))
    implementation (project(":errorreporting", "instrumentedJar"))
    implementation (project(":debugger", "instrumentedJar"))
    implementation (project(":sdlang", "instrumentedJar"))
    runtimeOnly (project(":dub", "instrumentedJar"))

    implementation (libs.gson) // used by dub parser

    testImplementation (libs.mockito.kotlin)

    testImplementation (libs.junit.engine)
    testRuntimeOnly (libs.junit.engine)
}

idea {
    module {
        generatedSourceDirs.add(file("gen"))
    }
}
