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
 * moved over from Java code Feb 2018. Class is essentially the same but now implements Dependency
 */
data class DubPackage(
    val name: String,
    val path: String,
    val version: String,
    val targetPath: String,
    val targetFileName: String,
    // the following can potentially be empty
    val description: String,
    val copyright: String,
    val license: String,
    val dependencies: List<String>,
    val sourcesDirs: List<String>, // importPaths
    val resources: List<String>, // stringImportPaths
    val sourceFiles: List<String>,
    val stringImportFiles: List<String>
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
    val packages: List<DubPackage> // after having the "rootPackage" removed
)
