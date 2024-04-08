package io.github.intellij.dlanguage.tools

import com.intellij.util.text.SemVer

/**
 * @author Samael Bate (singingbush)
 * created on 19/08/18
 */
class DtoolUtils {

    companion object {

        // This list of D Tool versions will need to be manually maintained until we have a web service in place
        const val DMD_LATEST: String = "2.106.0"
        const val LDC_LATEST: String = "1.35.0"

        const val SERVE_D_LATEST: String = "0.7.5"

        const val DUB_LATEST: String = "1.35.0"
        const val DSCANNER_LATEST: String = "v0.15.2" // v0.16.0-beta.4
        const val DCD_LATEST: String = "v0.15.2" // v0.9.10, OR v0.9.10-alpha.0
        const val DFORMAT_LATEST: String = "v0.15.1"

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
                    .filterNot { s -> s.isEmpty() }
                    .map {
                        it.split(Regex("\\D"))
                            .filterNot { s -> s.isEmpty() }
                            .first()
                    }
                    .map {
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
                        it.isBlank()
                    }.isNotEmpty()
        }

    }
}

