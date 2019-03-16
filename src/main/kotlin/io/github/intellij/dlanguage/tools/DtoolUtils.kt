package io.github.intellij.dlanguage.tools

import com.intellij.util.text.SemVer

/**
 * @author Samael Bate (singingbush)
 * created on 19/08/18
 */
class DtoolUtils {

    companion object {

        // This list of D Tool versions will need to be manually maintained until we have a web service in place
        const val DMD_LATEST: String = "2.084.1"
        const val LDC_LATEST: String = "1.13.0"

        const val SERVE_D_LATEST: String = "0.3.0"

        const val DUB_LATEST: String = "1.13.0"
        const val DSCANNER_LATEST: String = "v0.6.0" // v0.5.0-rc1
        const val DCD_LATEST: String = "v0.11.0" // v0.9.10, OR v0.9.10-alpha.0
        const val DFORMAT_LATEST: String = "v0.9.0"
        const val DFIX_LATEST: String = "v0.3.2"

        @JvmStatic
        fun String?.versionPredates(comparison: String?): Boolean {
            return if (!this.isSemVer() && !comparison.isSemVer()) false
            else {
                this.toSemVer()?.let {
                    val v = it
                    comparison?.toSemVer()?.let {
                        when {
                            it.major != v.major -> it.major > v.major
                            it.minor != v.minor -> it.minor > v.minor
                            else -> it.patch > v.patch
                        }
                    }
                } ?: false
            }
        }

        @JvmStatic
        fun String?.toSemVer(): SemVer? {
            return if (this.isSemVer()) {
                val parts = this!!.split(".")
                    .map {
                        it.split(Regex("\\D")).filterNot { s -> s.isBlank() } [0]
                    }.map {
                        it.toInt()
                    }

                return when (parts.size) {
                    1 -> SemVer(this, parts[0], 0, 0)
                    2 -> SemVer(this, parts[0], parts[1], 0)
                    else -> SemVer(this, parts[0], parts[1], parts[2])
                }
            } else null
        }

        @JvmStatic
        fun String?.isSemVer(): Boolean {
            return if (this.isNullOrBlank()) false
            else this.contains(".") &&
                this.split(".")
                    .map {
                        it.replace(Regex("\\D"), "")
                    }.filterNot {
                        isBlank()
                    }.isNotEmpty()
        }

    }
}

