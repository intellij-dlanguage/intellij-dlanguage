package io.github.intellij.dub.service

import com.intellij.openapi.util.text.StringUtil
import io.github.intellij.dlanguage.settings.ToolKey
import io.github.intellij.dlanguage.tools.DtoolUtils
import java.nio.file.Files
import java.nio.file.Paths

object DubBinaryPathProvider {

    /**
     * Return the path on the Dub binary or null if the dub is unavailable (or missconfigured)
     */
    fun getDubPath(): String? {
        val path = ToolKey.DUB_KEY.path
        if (path != null)
            return path

        return getAutodetectPath()
    }

    fun getAutodetectPath(): String? =
        DtoolUtils.lookInStandardDirectories(ToolKey.DUB_KEY.name)

    fun isDubAvailable(): Boolean {
        val dubBinaryPath = getDubPath()
        return dubBinaryPath != null && verifyDubBinary(dubBinaryPath)
    }

    fun verifyDubBinary(path: String): Boolean {
        if (!Files.isExecutable(Paths.get(path)) || Files.isDirectory(Paths.get(path)))
            return false
        val output = getVersionOutput(path)
        return StringUtil.isNotEmpty(output) && output.startsWith("DUB")
    }

    fun getVersionOutput(path: String): String = DtoolUtils.getVersionOutput(path)

}
