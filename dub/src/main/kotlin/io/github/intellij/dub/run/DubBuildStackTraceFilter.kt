package io.github.intellij.dub.run

import com.intellij.execution.filters.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import java.awt.Color
import kotlin.NumberFormatException

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
        val D_SOURCE_PATH_FORMAT = Regex("""^(.*\.di?):?(\(\d+,\d+\)|\d+)?:?\s+(.*\n?)$""")

        private val BLUE = Color(39, 89, 230)
    }

    override fun applyFilter(line: String, entireLength: Int): Filter.Result? {
        val found = line.lineSequence()
            .flatMap { D_SOURCE_PATH_FORMAT.find(it)?.groupValues ?: emptyList() }
        if (found.toList().isNotEmpty()) {
            // then it's certain we can get a hyperlink to the source file and possibly the line number
            val groups = found.drop(1) // the first one will be entire string
                .toList()

            val file = LocalFileSystem
                .getInstance()
                .findFileByPath(project.basePath.plus("/").plus(groups[0].replace("\\", "/")))

            // negate 1 from the line number as IDEA does line numbers starting from
            val lineNumber = if(groups[1].isNotBlank()) groups[1].parseLineNumber() - 1 else 0
            val columnNumber = if(groups[1].isNotBlank()) groups[1].parseColumnNumber() - 1 else 0

            val offset = entireLength - line.length
            file?.let {
                return Filter.Result(offset, offset + groups[0].length,
                    OpenFileHyperlinkInfo(project, it, lineNumber, columnNumber),
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
            first() // when the test is "(23,25)" it's line number followed by column. We don't need the column
    else
        this

    return try {
        Integer.parseInt(number)
    } catch (e: NumberFormatException) {
        0 // zero is fine as a default line number, it'll just mean linking to the source file rather than exact line
    }
}

private fun String.parseColumnNumber(): Int {
    val number = if (this.startsWith("("))
        this.substringAfter("(")
            .substringBefore(")")
            .split(",")[1] // when the test is "(23,25)" it's line number followed by column. We need the column
    else
        return 0

    return try {
        Integer.parseInt(number)
    } catch (e: NumberFormatException) {
        0 // zeros is fin as default line number
    }
}
