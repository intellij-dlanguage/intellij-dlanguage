plugins {
    id("java")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.gradleIntelliJPlugin)
}


// Disable all Gradle Tasks for the gradle-intellij-plugin as we only use the plugin for the dependencies
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
}

intellij {
    version.set(providers.gradleProperty("ideaVersion").get())
    plugins.set(listOf("org.intellij.intelliLang", "com.intellij.java", "com.intellij.java.ide")) // IDEA only (plugin was previously "java")
}

dependencies {
    api(project(":dlang:plugin-api"))
    testImplementation(project(":dlang:plugin-impl"))
    implementation(project(":utils"))
    implementation(project(":errorreporting"))
}
