import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java")
    id("org.gradle.idea")
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

val regenOutputTargetBase = "build/generated/sources/rdmd-parser/java/main"

val generatePsiInterfaces = tasks.register<Exec>("generatePsiInterfaces") {
    description = "Generate PSI interfaces using rdmd"
    outputs.dir(regenOutputTargetBase)
    workingDir(regenOutputTargetBase)
    executable("rdmd")
    args("${rootProject.projectDir}/scripts/types_regen_script.d", "Interface")
    doLast {
        println(standardOutput)
        println("Dlang psi-api PSI Interfaces generated using rdmd")
    }
}

sourceSets {
    main {
        java.srcDirs(
            files("src/main/jflex"),
            files("src/main/java"),
            files("src/main/kotlin"),
            files(regenOutputTargetBase).builtBy(generatePsiInterfaces),
        )
    }
}

dependencies {
    implementation (project(":utils"))
    testImplementation (libs.junit.engine)
    testRuntimeOnly (libs.junit.engine)

    intellijPlatform {
        intellijIdea(providers.gradleProperty("ideaVersion").get())
        testFramework(TestFrameworkType.Platform)
    }
}
