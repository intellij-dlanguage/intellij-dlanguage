package io.github.intellij.dlanguage.dub.packageConfig

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider
import com.jetbrains.jsonSchema.extension.JsonSchemaProviderFactory
import com.jetbrains.jsonSchema.extension.SchemaType

class DubJsonSchemaProviderFactory : JsonSchemaProviderFactory, DumbAware {
    override fun getProviders(project: Project): List<JsonSchemaFileProvider> {
        return listOf(DubJsonSchemaFileProvider())
    }
}


class DubJsonSchemaFileProvider : JsonSchemaFileProvider {
    override fun isAvailable(file: VirtualFile): Boolean = file.name in listOf("dub.json", "package.json")
    override fun getName(): String = "dub.json schema"
    override fun getSchemaType(): SchemaType = SchemaType.embeddedSchema
    override fun isUserVisible(): Boolean = false
    override fun getSchemaFile(): VirtualFile? {
        return JsonSchemaProviderFactory.getResourceFile(DubJsonSchemaFileProvider::class.java, SCHEMA_PATH)
    }

    companion object {
        const val SCHEMA_PATH: String = "/jsonSchema/dub.json-schema.json"
    }


}
