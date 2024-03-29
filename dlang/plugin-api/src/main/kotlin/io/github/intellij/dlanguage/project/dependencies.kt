package io.github.intellij.dlanguage.project

/**
 * @since v1.16.2
 * @author Samael Bate (singingbush)
 * created on 06/02/18
 */
interface Dependency {
    fun getDisplayName(): String
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
