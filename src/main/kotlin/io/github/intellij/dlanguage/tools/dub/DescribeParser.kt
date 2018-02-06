package io.github.intellij.dlanguage.tools.dub

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import io.github.intellij.dlanguage.project.DubPackage
import io.github.intellij.dlanguage.project.DubProject
import java.util.*

/**
 * The code that was specific to actually parsing the json from 'dub describe' has
 * been pulled out from DubConfigurationParser.java
 *
 * @author Samael Bate (singingbush)
 * created on 06/02/18
 * @since v1.16.2
 */
interface DescribeParser {
    /**
     * @param json The 'dub describe' command dumps a load of json to stdout, use that output with this method.
     * @return A DubProject object which contains much (not all) of the data that was in the json.
     * @since v1.16.2
     */
    @Throws(DescribeParserException::class)
    fun parse(json: String): DubProject
}

class DescribeParserImpl : DescribeParser {

    // some handy extension functions for Gson:
    private fun JsonObject.asString(key: String): String = get( key )?.asString ?: ""
    private fun JsonObject.asStringArray(key: String): List<String> = get(key)?.asJsonArray?.map { it.asString } ?: emptyList()

    @Throws(DescribeParserException::class)
    override fun parse(json: String): DubProject {
        return try {
            JsonParser().parse(json).asJsonObject.let {
                val rootPackageName = it.asString("rootPackage")
                val allPackages = parsePackages(rootPackageName, it.get("packages").asJsonArray)

                DubProject(
                    rootPackageName,
                    allPackages.first,
                    it.asString("configuration"),
                    it.asString("buildType"),
                    it.asString("compiler"),
                    it.asStringArray("architecture"),
                    it.asStringArray("platform"),
                    allPackages.second
                )
            }
        } catch (e: JsonSyntaxException) {
            throw DescribeParserException("There was a problem parsing the result of dub describe", e)
        }
    }

    private fun parsePackages(rootPackageName: String, packages: JsonArray): Pair<DubPackage, List<DubPackage>> {
        var rootPackage: DubPackage? = null
        val packageList = ArrayList<DubPackage>(packages.size())

        packages
            .map { jsonToDubPackage(it as JsonObject) }
            .forEach {
                if(rootPackageName == it.name) {
                    rootPackage = it
                } else {
                    packageList.add(it)
                }
            }

        return rootPackage?.let { Pair<DubPackage, List<DubPackage>>(it, packageList) } ?: throw DescribeParserException("Could not establish root package")
    }

    private fun jsonToDubPackage(json: JsonObject): DubPackage {
        return DubPackage(
            name = json.asString("name"),
            path = json.asString("path"),
            version = json.asString("version"),
            description = json.asString("description"),
            copyright = json.asString("copyright"),
            license = json.asString("license"),
            dependencies = json.asStringArray("dependencies"),
            sourcesDirs = json.asStringArray("importPaths"), // importPaths, eg: "source",
            resources = json.asStringArray("stringImportPaths"), // stringImportPaths, eg: "views",
            sourceFiles = json.asStringArray("sourceFiles"),
            stringImportFiles = json.asStringArray("stringImportFiles"))
    }
}

/**
 * @author Samael Bate (singingbush)
 * created on 06/02/18
 * @since v1.16.2
 */
class DescribeParserException(message: String, cause: Throwable? = null) : Throwable(message, cause)
