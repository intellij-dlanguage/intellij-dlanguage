package io.github.intellij.dlanguage.run

import com.intellij.execution.filters.*
import com.intellij.openapi.editor.markup.EffectType
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import java.awt.Color
import java.awt.Font
import java.lang.NumberFormatException

class DubConsoleFilterProvider : ConsoleFilterProvider {
    override fun getDefaultFilters(project: Project): Array<Filter> = arrayOf(DubBuildSourceFileFilter(project))
}

/**
 * Will get a match on output that contains a path to a D source file and make the filepath a clickable link.
 * The filter checks for the line to start with "source" as it's more likely to be a project source file.
 */
class DubBuildSourceFileFilter(val project: Project) : Filter {

    internal companion object {
        // This regex will get a match on any of the following:
        //     source\package\app.d Some Log output
        //     source\package\app.d(53,31) Some Log output
        //     source\package\app.di(53,31): Some Log output
        //     source\package\app.di Some Log output
        //     source\path\app.d:21 Some text output
        val D_SOURCE_PATH_FORMAT = Regex("^(.*\\.di?):?(\\(\\d+,\\d+\\)|\\d+)?:?\\s+(.*)\$")

        private val BLUE = Color(39, 89, 230)
    }

    override fun applyFilter(line: String, entireLength: Int): Filter.Result? {
        if((line.startsWith("source") || line.startsWith("src")) && line.matches(D_SOURCE_PATH_FORMAT)) {
            // then it's fairly certain we can get a hyperlink to the source file and possibly the line number
            val groups = line.lineSequence()
                .flatMap { D_SOURCE_PATH_FORMAT.find(it)?.groupValues ?: emptyList() }
                .drop(1) // the first one will be entire string
                .toList()

            val file = LocalFileSystem
                .getInstance()
                .findFileByPath(project.basePath.plus("/").plus(groups[0].replace("\\", "/")))

            // negate 1 from the line number as IDEA does line numbers starting from
            val lineNumber = if(groups[1].isNotBlank()) groups[1].parseLineNumber() -1 else 0

            file?.let {
                return Filter.Result(0, groups[0].length,
                    OpenFileHyperlinkInfo(project, it, lineNumber),
                    TextAttributes(BLUE, null, null, EffectType.BOLD_LINE_UNDERSCORE, Font.ITALIC)
                )
            }
        }
        return null
    }
}

private fun String.parseLineNumber(): Int {
    val number = if(this.startsWith("("))
        this.substringAfter("(")
            .substringBefore(")")
            .split(",").
            first() // when the test is "(23:25)" it's line number followed by column. We don't need the column
    else
        this

    return try {
        Integer.parseInt(number)
    } catch (e: NumberFormatException) {
        0 // zero is fine as a default line number, it'll just mean linking to the source file rather than exact line
    }
}

