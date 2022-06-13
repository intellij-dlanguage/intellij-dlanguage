package io.github.intellij.dlanguage.tools.dub

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import io.github.intellij.dlanguage.project.BuildSettings
import io.github.intellij.dlanguage.project.DubPackage
import io.github.intellij.dlanguage.project.DubProject
import io.github.intellij.dlanguage.project.DubTarget
import java.io.Reader
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
     * @param reader The 'dub describe' command dumps a load of json to stdout, allocate a Reader for the stdout and pass it in.
     * @return A DubProject object which contains much (not all) of the data that was in the json.
     * @since v1.28.1
     */
    @Throws(DescribeParserException::class)
    fun parse(reader: Reader): DubProject

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
    override fun parse(reader: Reader): DubProject {
        return try {
            JsonParser.parseReader(reader).asJsonObject.let {
                processJsonObject(it)
            }
        } catch (e: JsonSyntaxException) {
            throw DescribeParserException("There was a problem parsing the result of dub describe", e)
        }
    }

    @Throws(DescribeParserException::class)
    override fun parse(json: String): DubProject {
        return try {
            JsonParser.parseString(json).asJsonObject.let {
                processJsonObject(it)
            }
        } catch (e: JsonSyntaxException) {
            throw DescribeParserException("There was a problem parsing the result of dub describe", e)
        }
    }

    private fun processJsonObject(jsonData: JsonObject): DubProject {
        val rootPackageName = jsonData.asString("rootPackage")
        val allPackages = parsePackages(rootPackageName, jsonData.get("packages").asJsonArray)

        return DubProject(
            rootPackageName,
            allPackages.first,
            jsonData.asString("configuration"),
            jsonData.asString("buildType"),
            jsonData.asString("compiler"),
            jsonData.asStringArray("architecture"),
            jsonData.asStringArray("platform"),
            allPackages.second,
            //parseTargets(jsonData.get("targets").asJsonArray)
        )
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

//    private fun parseTargets(jsonArray: JsonArray): List<DubTarget> {
//        return jsonArray
//            .map { it as JsonObject }
//            .map { DubTarget(
//                it.asString("rootPackage"),
//                it.asStringArray("packages"),
//                it.asString("rootConfiguration"),
//                BuildSettings() //it.asString("buildSettings")
//            ) }
//    }

    private fun jsonToDubPackage(json: JsonObject): DubPackage {
        return DubPackage(
            name = json.asString("name"),
            path = json.asString("path"),
            version = json.asString("version"),
            targetPath = json.asString("targetPath"),
            targetFileName = json.asString("targetFileName"),
            description = json.asString("description"),
            copyright = json.asString("copyright"),
            license = json.asString("license"),
            dependencies = json.asStringArray("dependencies"),
            sourcesDirs = json.asStringArray("importPaths"), // importPaths, eg: "source",
            resources = json.asStringArray("stringImportPaths"), // stringImportPaths, eg: "views",
            sourceFiles = json.asStringArray("sourceFiles"),
            stringImportFiles = json.asStringArray("stringImportFiles"),
            libs = json.asStringArray("libs"),
            copyFiles = json.asStringArray("copyFiles"),
            extraDependencyFiles = json.asStringArray("extraDependencyFiles"),
            versions = json.asStringArray("versions"),
            debugVersions = json.asStringArray("debugVersions"),
            preGenerateCommands = json.asStringArray("preGenerateCommands"),
            postGenerateCommands = json.asStringArray("postGenerateCommands"),
            preBuildCommands = json.asStringArray("preBuildCommands"),
            postBuildCommands = json.asStringArray("postBuildCommands"),
            preRunCommands = json.asStringArray("preRunCommands"),
            postRunCommands = json.asStringArray("postRunCommands"),
            //environments = json.asStringArray("environments"),
            //buildEnvironments = json.asStringArray("buildEnvironments"),
            //runEnvironments = json.asStringArray("runEnvironments"),
            //preGenerateEnvironments = json.asStringArray("preGenerateEnvironments"),
            //postGenerateEnvironments = json.asStringArray("postGenerateEnvironments"),
            //preBuildEnvironments = json.asStringArray("preBuildEnvironments"),
            //postBuildEnvironments = json.asStringArray("postBuildEnvironments"),
            //preRunEnvironments = json.asStringArray("preRunEnvironments"),
            //postRunEnvironments = json.asStringArray("postRunEnvironments"),
            options = json.asStringArray("options"),
            //files = json.asJsonArray("files")
        )
    }
}

/**
 * @author Samael Bate (singingbush)
 * created on 06/02/18
 * @since v1.16.2
 */
class DescribeParserException(message: String, cause: Throwable? = null) : Throwable(message, cause)
