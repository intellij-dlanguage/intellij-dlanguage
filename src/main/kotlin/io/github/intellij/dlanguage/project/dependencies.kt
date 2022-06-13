package io.github.intellij.dlanguage.project

/**
 * @since v1.16.2
 * @author Samael Bate (singingbush)
 * created on 06/02/18
 */
interface Dependency {
    fun getDisplayName(): String
}

data class DubPackageFile(
    val role: String, // eg: "source", "unusedSource", "stringImport", or "import_"
    val path: String  // the relative path from package root
)

/**
 * moved over from Java code Feb 2018. Class is essentially the same but now implements Dependency
 */
data class DubPackage(
    val name: String,
    val path: String,
    val version: String,
    //val targetType: String,
    val targetPath: String,
    //val targetName: String,
    val targetFileName: String,
    // the following can potentially be empty
    val description: String,
    val homepage: String = "",
    val authors: List<String> = emptyList(),
    val copyright: String,
    val license: String,
    val dependencies: List<String>,
    //val active: Boolean,
    //val configuration: String,
    val workingDirectory: String = "",
    val mainSourceFile: String = "",
    //val dflags: List<String> = emptyList(),
    //val lflags: List<String> = emptyList(),
    val libs: List<String> = emptyList(),
    val copyFiles: List<String> = emptyList(),
    val extraDependencyFiles: List<String> = emptyList(),
    val versions: List<String> = emptyList(),
    val debugVersions: List<String> = emptyList(),
//    val importPaths: List<String> = emptyList(),
//    val stringImportPaths: List<String> = emptyList(),
    val sourcesDirs: List<String>, // importPaths
    val resources: List<String>, // stringImportPaths
    val sourceFiles: List<String>,
    val stringImportFiles: List<String>,
    val preGenerateCommands: List<String> = emptyList(),
    val postGenerateCommands: List<String> = emptyList(),
    val preBuildCommands: List<String> = emptyList(),
    val postBuildCommands: List<String> = emptyList(),
    val preRunCommands: List<String> = emptyList(),
    val postRunCommands: List<String> = emptyList(),
    val environments: Map<String, String> = emptyMap(),
    val buildEnvironments: Map<String, String> = emptyMap(),
    val runEnvironments: Map<String, String> = emptyMap(),
    val preGenerateEnvironments: Map<String, String> = emptyMap(),
    val postGenerateEnvironments: Map<String, String> = emptyMap(),
    val preBuildEnvironments: Map<String, String> = emptyMap(),
    val postBuildEnvironments: Map<String, String> = emptyMap(),
    val preRunEnvironments: Map<String, String> = emptyMap(),
    val postRunEnvironments: Map<String, String>? = emptyMap(),
    val options: List<String> = emptyList(),
    val files: List<DubPackageFile> = emptyList()
) : Dependency {
    override fun getDisplayName(): String = "$name-$version"
}

/**
 * This should be used to represent a dependency in a project that is not using dub
 * @since v1.16.2
 * @author Samael Bate (singingbush)
 * created on 06/02/18
 */
data class LocalPackage(
    val name: String,
    val path: String
) : Dependency {
    override fun getDisplayName(): String = name
}

data class BuildSettings(
    val targetType: Int,
    val targetPath: String,
    val targetName: String,
    val workingDirectory: String = "",
    val mainSourceFile: String = "",
    val dflags: List<String> = emptyList(),
    val lflags: List<String> = emptyList(),
    val libs: List<String>,
    val linkerFiles: List<String>,
    val sourceFiles: List<String>,
    val copyFiles: List<String> = emptyList(),
    val extraDependencyFiles: List<String> = emptyList(),
    val versions: List<String>,
    val debugVersions: List<String> = emptyList(),
    val versionFilters: List<String> = emptyList(),
    val debugVersionFilters: List<String> = emptyList(),
    val importPaths: List<String>,
    val stringImportPaths: List<String>,
    val importFiles: List<String> = emptyList(),
    val stringImportFiles: List<String>,
    val preGenerateCommands: List<String> = emptyList(),
    val postGenerateCommands: List<String> = emptyList(),
    val preBuildCommands: List<String> = emptyList(),
    val postBuildCommands: List<String> = emptyList(),
    val preRunCommands: List<String> = emptyList(),
    val postRunCommands: List<String> = emptyList()
)

data class DubTarget(
    val rootPackage: String,
    val packages: List<String> = emptyList(),
    val rootConfiguration: String,
    val buildSettings: BuildSettings
)

/**
 * This object represents the data that is parsed when running 'dub describe'
 *
 * Do not remove anything from this file without annotating it as deprected for a few releases
 *
 * @author Samael Bate (singingbush)
 * created on 06/02/18
 * @since v1.16.2
 */
data class DubProject(
    val rootPackageName: String, // this is "rootPackage" in the json
    val rootPackage: DubPackage, // this is actually in the packages[] but filter it out and place here
    val configuration: String,
    val buildType: String,
    val compiler: String,
    val architecture:  List<String>, // eg: [x86_64"],
    val platform: List<String>, // eg: ["linux","posix"],
    val packages: List<DubPackage>, // after having the "rootPackage" removed
    val targets: List<DubTarget> = emptyList()
)
