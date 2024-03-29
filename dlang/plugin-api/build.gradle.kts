import org.jetbrains.grammarkit.tasks.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

fun properties(key: String) = providers.gradleProperty(key).get()

plugins {
    id("org.gradle.idea")
    id("java")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.gradleIntelliJPlugin)
    alias(libs.plugins.grammarkit)
//    id("net.saliman.cobertura") version "4.0.0"
//    id("com.github.kt3k.coveralls") version "2.10.2"
}

val javaVersion = properties("javaVersion")
val ideaVersion = properties("ideaVersion")

val publishChannels = listOf(properties("publishChannels"))

val baseIDE = "idea"

// cobertura.coverageFormats = ["html", "xml"] // coveralls plugin depends on xml format report
// cobertura.coverageSourceDirs = [sourceSets.main.java.srcDirs, sourceSets.main.kotlin.srcDirs, "gen"]
// cobertura.coverageEncoding = "UTF-8"
// cobertura.coverageExcludes = [ ".*uk.co.cwspencer.*" ]

sourceSets {
    main {
        java.srcDirs("src/main/java", "src/main/kotlin", "gen" , "src/main/jflex")
        // resources.srcDirs("src/main/resources") // specifying the default causes a problem with processResources on Gradle 7
    }
    test {
        java.srcDirs("src/test/java", "src/test/kotlin")
    }
}

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
        buildPlugin { enabled = false }
        buildSearchableOptions { enabled = false }
        downloadRobotServerPlugin { enabled = false }
        jarSearchableOptions { enabled = false }
        patchPluginXml { enabled = false }
        prepareSandbox { enabled = false }
        prepareTestingSandbox { enabled = false }
        prepareUiTestingSandbox { enabled = false }
        publishPlugin { enabled = false }
        runIde { enabled = false }
        runIdeForUiTests { enabled = false }
        runPluginVerifier { enabled = false }
        signPlugin { enabled = false }
        verifyPlugin { enabled = false }
        test { enabled = true }

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

val generateSyntaxLexer = tasks.register<GenerateLexerTask>("generateSyntaxLexer") {
    // source flex file
    sourceFile.set(file("src/main/jflex/io/github/intellij/dlanguage/lexer/DLanguageLexer.flex"))

    // target directory for lexer
    targetOutputDir.set(file("gen/io/github/intellij/dlanguage/"))
}

val generateDocSyntaxLexer = tasks.register<GenerateLexerTask>("generateDocSyntaxLexer") {
    // source flex file
    sourceFile.set(file("src/main/kotlin/io/github/intellij/dlanguage/features/documentation/DDocLexer.flex"))

    // target directory for lexer
    targetOutputDir.set(file("gen/io/github/intellij/dlanguage/features/documentation/"))
}

val copyGenScript = tasks.register<Copy>("copyGenScript") {
    from("src/main/resources/types_regen_script.d")
    into("gen/io/github/intellij/dlanguage/psi/")
}

val generatePsi = tasks.register<Exec>("generatePsi") {
    dependsOn(copyGenScript)
    workingDir("gen/io/github/intellij/dlanguage/psi/")
    commandLine("rdmd", "types_regen_script.d")
}

tasks.withType<JavaCompile>().configureEach {
    dependsOn(
        generateSyntaxLexer,
        generateDocSyntaxLexer,
        generatePsi
    )
}

tasks.withType<KotlinCompile>().configureEach {
    dependsOn(
        generateSyntaxLexer,
        generateDocSyntaxLexer,
        generatePsi
    )
}

dependencies {
    // According to https://www.jetbrains.org/intellij/sdk/docs/tutorials/kotlin.html
    // we should not need to include kotlin-runtime and kotlin-stdlib jars with a plugin
    // as it's bundled with Intellij, however if we support multiple Intellij versions (which
    // we do) then it may be worth bundling our own kotlin jars
    //compileOnly "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
    //compileOnly "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"

    // todo: When updating the gradle intellij plugin we'll most likely need to set
    // some project dependencies with the 'instrumentedJar' configuration
    // see:
    //  https://github.com/JetBrains/gradle-intellij-plugin/issues/1412
    //  https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html#multi-module-project
    implementation (project(":utils"))
    implementation (project(":errorreporting"))
    implementation (project(":debugger"))
    implementation (project(":sdlang"))

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
