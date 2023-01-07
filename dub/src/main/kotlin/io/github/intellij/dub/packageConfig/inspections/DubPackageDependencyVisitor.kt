package io.github.intellij.dub.packageConfig.inspections

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.json.psi.*
import com.intellij.psi.PsiFile

abstract class DubJsonDependencyVisitor(val holder: ProblemsHolder) : JsonElementVisitor() {

    override fun visitProperty(o: JsonProperty) {
        val currentLibParentObject = o.parent as? JsonObject ?: return
        val parentBlockProperty = currentLibParentObject.parent as? JsonProperty ?: return
        if (parentBlockProperty.name != "dependencies")
            return
        val topLevelObject = parentBlockProperty.parent as? JsonObject ?: return
        if (topLevelObject.parent !is PsiFile)
            return

        val dependency = JsonPackageDependency(o.name, o, collectElements(o))
        visitPackage(dependency)
    }

    /**
     * Implement this method to visit a dependency in dub.json
     */
    abstract fun visitPackage(packageDependency: JsonPackageDependency)
}

data class JsonPackageDependency(
    val name: String,
    val element: JsonProperty,
    val properties: Map<String, JsonElement>)

fun collectElements(packageElement: JsonProperty) : Map<String, JsonElement> {
    if (packageElement.value !is JsonObject) {
        if (packageElement.value == null)
            return mapOf()
        return mapOf("version" to packageElement.value!!)
    }
    val properties = packageElement.value as JsonObject
    return properties.propertyList.mapNotNull {
        val name = it.name
        val value = it.value ?: return@mapNotNull null
        name to value
    }.toMap()
}
