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

    private val blue = Color(39, 89, 230)

    override fun applyFilter(line: String, entireLength: Int): Filter.Result? {
        if(line.startsWith("source")) {
            // then it's prob code within the project
            val txt = if (line.contains(":")) {
                line.substring(0, line.indexOf(":").plus(1))
            } else {
                line
            }

            val filePath = txt.substringBefore("(")
            val lineColumn = txt
                .substringAfter("(")
                .substringBefore(")")
                .split(",")
                .map { Integer.parseInt(it) }

            val fullFilePath = project.basePath.plus("/").plus(filePath.replace("\\", "/"))
            val virtualFile = LocalFileSystem.getInstance().findFileByPath(fullFilePath)

            virtualFile?.let {
                return Filter.Result(entireLength - line.length, (entireLength - line.length) + txt.lastIndex,
                    DlangSourceFileHyperlink(it, project, lineColumn[0] - 1, lineColumn[1]), // consider OpenFileHyperlinkInfo(project, it, lineColumn[0] - 1, lineColumn[1])
                    TextAttributes(blue, null, null, EffectType.BOLD_LINE_UNDERSCORE, Font.ITALIC)
                )
            }
        }
        return null
    }
}


class DlangSourceFileHyperlink(override val virtualFile: VirtualFile?,
                         project: Project, lineNumber: Int, column: Int)
    : FileHyperlinkInfoBase(project, lineNumber, column)
