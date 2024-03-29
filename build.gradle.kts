import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

// The same as `--stacktrace` param
gradle.startParameter.showStacktrace = ShowStacktrace.ALWAYS

fun properties(key: String) = providers.gradleProperty(key).get()

plugins {
    id("java")
    alias(libs.plugins.kotlin)
//    id("net.saliman.cobertura") version "4.0.0"
//    id("com.github.kt3k.coveralls") version "2.10.2"
}


version = properties("version")

// When testing, set to "true" if you want to have expected data written (to easily update lexer/parser tests)
val overrideTestData = "false"


allprojects {

    repositories {
        mavenCentral()
    }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = JavaVersion.VERSION_17.majorVersion
            targetCompatibility = JavaVersion.VERSION_17.majorVersion
            options.encoding = "UTF-8"
            options.compilerArgs.add("-Xlint:deprecation")
        }

        withType<KotlinCompile> {
            compilerOptions {
                apiVersion.set(KotlinVersion.KOTLIN_1_7)
                languageVersion.set(KotlinVersion.KOTLIN_1_8)
                jvmTarget.set(JvmTarget.JVM_17)
                //allWarningsAsErrors.set(true)
            }
        }

        withType<Test> {
            systemProperty("idea.tests.overwrite.data", overrideTestData)
            systemProperty("java.awt.headless", "true") // avoid "Must be precomputed" error, because IDE is not started (LoadingState.APP_STARTED.isOccurred is false)

//            jvmArgs = listOf(
//                "--illegal-access=warn",
//                "--add-opens=java.base/java.lang=ALL-UNNAMED",
//                "--add-opens=java.base/java.io=ALL-UNNAMED",
//                "--add-opens=java.desktop/sun.awt=ALL-UNNAMED",
//                "--add-opens=java.desktop/java.awt=ALL-UNNAMED",
//                "--add-opens=java.desktop/java.awt.event=ALL-UNNAMED",
//                "--add-opens=java.desktop/javax.swing=ALL-UNNAMED"
//            )
        }
    }

}
