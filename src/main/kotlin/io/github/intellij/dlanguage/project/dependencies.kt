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
    val dependencies: List<String>,
    val sourcesDir: String, // todo: this should prob be String[]
    val resources: List<String>,
    val version: String,
    val isRootPackage: Boolean
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
