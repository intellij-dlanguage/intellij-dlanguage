package io.github.intellij.dlanguage.types

/**
 * A type fingerprint used for indexing. It should satisfy two properties:
 *
 *  * `ty1 == ty2 => fingerprint(ty1) == fingerprint(ty2)`.
 *  * fingerprint can be computed without name resolution.
 */
data class DTypeFingerprint constructor(
    private val name: String
) {

}
