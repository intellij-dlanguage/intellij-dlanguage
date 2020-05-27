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

/**
 * Will get a match on output that contains a path to a D source file and make the filepath a clickable link.
 * The filter checks for the line to start with "source" as it's more likely to be a project source file.
 */
class DubBuildSourceFileFilter(val project: Project) : Filter {

    private companion object {
        // This regex will get a match on any of the following:
        //     source\package\app.d Some Log output
        //     source\package\app.d(53,31) Some Log output
        //     source\package\app.di(53,31): Some Log output
        //     source\package\app.di Some Log output
        val D_SOURCE_PATH_FORMAT = Regex("^(.*\\.di?)(\\(\\d+,\\d+\\))?(:)?.*\$")

        private val BLUE = Color(39, 89, 230)
    }

    override fun applyFilter(line: String, entireLength: Int): Filter.Result? {
        if(line.startsWith("source") && line.matches(D_SOURCE_PATH_FORMAT)) {
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
                    TextAttributes(BLUE, null, null, EffectType.BOLD_LINE_UNDERSCORE, Font.ITALIC)
                )
            }
        }
        return null
    }
}


class DlangSourceFileHyperlink(override val virtualFile: VirtualFile?,
                         project: Project, lineNumber: Int, column: Int)
    : FileHyperlinkInfoBase(project, lineNumber, column)
