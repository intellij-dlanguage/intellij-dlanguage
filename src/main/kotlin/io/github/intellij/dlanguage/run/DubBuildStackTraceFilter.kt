package io.github.intellij.dlanguage.run

import com.intellij.execution.filters.*
import com.intellij.openapi.editor.markup.EffectType
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import java.awt.Color
import java.awt.Font

class DubConsoleFilterProvider : ConsoleFilterProvider {
    override fun getDefaultFilters(project: Project): Array<Filter> = arrayOf(DubBuildSourceFileFilter(project))
}

class DubBuildSourceFileFilter(val project: Project) : Filter {
    override fun applyFilter(line: String?, entireLength: Int): Filter.Result? {
        line?.let {
            if(it.startsWith("source")) {
                // then it's prob code within the project
                val txt = if (it.contains(":")) {
                    it.substring(0, it.indexOf(":"))
                } else {
                    it
                }

                val filePath = txt.substringBefore("(")
                val lineColumn = txt.substringAfter("(")
                    .replace(")", "")
                    .split(",")
                    .map { Integer.parseInt(it) }

                val fullFilePath = project.basePath.plus("/").plus(filePath.replace("\\", "/"))
                val virtualFile = LocalFileSystem.getInstance().findFileByPath(fullFilePath)

                return Filter.Result(entireLength - line.length, (entireLength - line.length) + txt.lastIndex,
                    DlangSourceFileHyperlink(virtualFile, project, lineColumn[0] - 1, lineColumn[1]),
                    TextAttributes(Color(39, 89, 230), null, null, EffectType.BOLD_LINE_UNDERSCORE, Font.ITALIC)
                )
            }
        }
        return null
    }
}


class DlangSourceFileHyperlink(override val virtualFile: VirtualFile?,
                         project: Project, lineNumber: Int, column: Int)
    : FileHyperlinkInfoBase(project, lineNumber, column)
