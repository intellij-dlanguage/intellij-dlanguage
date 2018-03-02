package io.github.intellij.dlanguage.codeinsight.dcd

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.configurations.ParametersList
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleUtilCore
import com.intellij.psi.PsiFile
import io.github.intellij.dlanguage.codeinsight.dcd.completions.DCDModel
import io.github.intellij.dlanguage.settings.ToolKey
import io.github.intellij.dlanguage.utils.DUtil
import io.github.intellij.dlanguage.utils.ExecUtil

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.ArrayList


data class Response(val identifiers: List<Identifier>,val type: String)
data class Identifier(val definition: String, val documentation:String, val file: String, val identifier: String,val location:Int, val type: String)
/*
public String definition;
public String documentation;
public String file;
public String identifier;
public Integer location;
public String type;
 */

class DCDCompletionClient
{
    private val completions = ArrayList<DCDModel>()
    private val gson:Gson = Gson()

    fun autoComplete(position: Int, file: PsiFile): List<DCDModel>
    {

        val module = ModuleUtilCore.findModuleForPsiElement(file)

        completions.clear()

        if (module != null)
        {
            val path = lookupPath()
            if (path != null)
            {

                val server = module.getComponent(DCDCompletionServer::class.java)

                try
                {
                    server.exec()
                } catch (dcdError: DCDCompletionServer.DCDError)
                {
                    dcdError.printStackTrace()
                }

                val workingDirectory = file.project.basePath

                val result = server.listCompletion(file.text, position)

                println("Result: $result")

                val response = gson.fromJson(result, Response::class.java)

                if(response.type == "identifiers")
                {
                    for (identifier in response.identifiers)
                    {
                        var type: String? = null
                        if(identifier.definition.length > 0)
                        {
                            var defs = identifier.definition.split(" ")
                            if(defs.size == 2)
                                type = defs[0]
                        }
                        val model = DCDModel(identifier.identifier, identifier.type, type)
                        completions.add(model)
                    }
                }
                else
                    println("Unsupported response type: ${response.type}")


                /*
                val commandLine = GeneralCommandLine()
                commandLine.setWorkDirectory(workingDirectory)
                commandLine.exePath = path

                val parametersList = commandLine.parametersList
                parametersList.addParametersString("-c")
                parametersList.addParametersString(position.toString())
                parametersList.addParametersString("--extended")

                val flags = ToolKey.DCD_CLIENT_KEY.flags

                if (DUtil.isNotNullOrEmpty(flags))
                {
                    val importList = flags.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    for (i in importList.indices)
                    {
                        parametersList.addParametersString("-I")
                        parametersList.addParametersString(importList[i])
                    }
                }

                val filePath = file.virtualFile.path


                parametersList.add(file.virtualFile.path)

                val result = ExecUtil.readCommandLine(commandLine, null)

                if (result != null && !result.isEmpty())
                    handleResult(result)
                */
            }
        }
        return completions
    }

    private fun handleResult(result: String)
    {
        if (result.startsWith("identifiers"))
        {
            val tokens = result.split("\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            // first pass: add user code first!
            for (i in tokens.indices)
            {
                val token = tokens[i]
                val parts = token.split("\\t".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                if (parts.size > 2)
                {
                    completions.add(DCDModel(parts[0], parts[1], parts[2].split("\\s".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]))
                }
            }

            // second pass: add the rest
            for (i in tokens.indices)
            {
                val token = tokens[i]
                val parts = token.split("\\t".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                if (parts.size == 2)
                {
                    completions.add(DCDModel(parts[0], parts[1]))
                }
            }

        } else if (result.startsWith("calltips"))
        {
            //TODO: handle calltips
        }
    }


    private fun lookupPath(): String?
    {
        return ToolKey.DCD_CLIENT_KEY.path
    }
}
