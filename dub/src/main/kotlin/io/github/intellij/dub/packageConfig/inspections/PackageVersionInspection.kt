package io.github.intellij.dub.packageConfig.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.json.psi.JsonStringLiteral
import com.intellij.openapi.util.text.StringUtil.isLatinAlphanumeric
import com.intellij.psi.PsiElementVisitor
import com.intellij.util.text.SemVer

class PackageVersionValidationInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        if (holder.file.name != "dub.json") return super.buildVisitor(holder, isOnTheFly)
        return JsonDependencyVersionVisitor(holder)
    }
}

class JsonDependencyVersionVisitor(holder: ProblemsHolder) : DubJsonDependencyVisitor(holder) {

    override fun visitPackage(packageDependency: JsonPackageDependency) {
        if (packageDependency.properties["version"] == null)
            return
        if (packageDependency.properties["version"] !is JsonStringLiteral) {
            holder.registerProblem(
                packageDependency.properties["version"]!!,
                "Version must be a string",
                ProblemHighlightType.ERROR)
            return
        }
        val packageVersionElement = packageDependency.properties["version"] as JsonStringLiteral
        val dependencyVersion = packageVersionElement.value

        if (dependencyVersion == "*")
            return
        // Check if minor simple (~>X.Y.Z)
        if ((dependencyVersion.startsWith("~>") || dependencyVersion.startsWith("=="))
            && isSemVer(dependencyVersion.substring(2))
        ) {
            return
        }
        val range = dependencyVersion.split(" ")
        if (range.size > 2) {
            holder.registerProblem(
                packageVersionElement,
                "Invalid version requirement $dependencyVersion",
                ProblemHighlightType.ERROR)
            return
        }
        if (range.all { (it.startsWith(">") || it.startsWith("<")
                || it.startsWith(">=") || it.startsWith(">=")) &&
                isSemVer(it.trimStart('>', '<', '='))
            }) {
            if (range.size == 2) {
                val lessThanOrEquals = range[0].startsWith("<=")
                if (range[0].startsWith("<=") || lessThanOrEquals) {
                    val prefix = if (lessThanOrEquals) "<=" else "<"
                    holder.registerProblem(
                        packageVersionElement,
                        "First comparison operator expected to be either > or >=, not $prefix",
                        ProblemHighlightType.ERROR)
                    return
                }
                if (toSemVer(range[0].trimStart('>', '<', '='))!! > toSemVer(range[1].trimStart('>', '<', '='))!!) {
                    holder.registerProblem(
                        packageVersionElement,
                        "First comparison operator should not be greater to the second",
                        ProblemHighlightType.ERROR)
                    return
                }
            }
            return
        }
        // try raw version (X.Y.Z)
        if (isSemVer(dependencyVersion)) {
            return
        }
        // try branch version
        if (dependencyVersion.startsWith("~") && isLatinAlphanumeric(dependencyVersion.substring(1))) {
            return
        }
        holder.registerProblem(
            packageVersionElement,
            "Invalid version requirement $dependencyVersion",
            ProblemHighlightType.ERROR)
    }
}

fun isSemVer(text: String?): Boolean {
    if (text == null) return false
    return toSemVer(text) != null
}

fun toSemVer(text: String): SemVer? {
    val endIndex = if (text.lastIndexOf("+") > 0) text.lastIndexOf("+") else text.lastIndex + 1
    return SemVer.parseFromText(text.substring(0, endIndex))
}

