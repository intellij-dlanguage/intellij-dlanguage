import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.utils.asPath
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Files

plugins {
    id("java")
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

sourceSets {
    main {
        java.srcDirs("src/main/java", "src/main/kotlin", "gen" , "src/main/jflex")
    }
    test {
        java.srcDirs("src/test/java", "src/test/kotlin")
    }
}


val ensureDirectory = tasks.register("ensureDirectory") {
    val file = file("gen/io/github/intellij/dlanguage/psi/")
    doLast {
        Files.createDirectories(file.toPath())
    }
}

val generatePsi = tasks.register<Exec>("generatePsi") {
    dependsOn(ensureDirectory)
    workingDir("gen/io/github/intellij/dlanguage/psi/")
    executable("rdmd")
    args("${rootProject.projectDir}/scripts/types_regen_script.d", "Interface")
}

tasks.withType<JavaCompile>().configureEach {
    dependsOn(
        generatePsi,
    )
}

tasks.withType<KotlinCompile>().configureEach {
    dependsOn(
        generatePsi,
    )
}

dependencies {
    implementation (project(":utils"))
    testImplementation (libs.junit.engine)
    testRuntimeOnly (libs.junit.engine)

    intellijPlatform {
        intellijIdeaCommunity(providers.gradleProperty("ideaVersion").get())
        instrumentationTools()
        testFramework(TestFrameworkType.Platform)
    }
}
