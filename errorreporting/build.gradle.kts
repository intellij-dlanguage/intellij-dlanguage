plugins {
    id("java")
    alias(libs.plugins.kotlin)
    // todo: Rework the utils module to just use com.jetbrains.intellij.platform:core and com.jetbrains.intellij.platform:lang
    // so that there"s no need to use "org.jetbrains.intellij" plugin in this module
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
}

dependencies {
    implementation(libs.sentry)
}
