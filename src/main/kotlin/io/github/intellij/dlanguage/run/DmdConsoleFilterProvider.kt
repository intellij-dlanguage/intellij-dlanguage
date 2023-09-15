package io.github.intellij.dlanguage.run

import com.intellij.execution.filters.ConsoleFilterProviderEx
import com.intellij.execution.filters.Filter
import com.intellij.execution.filters.OpenFileHyperlinkInfo
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.search.GlobalSearchScope

class DmdConsoleFilterProvider : ConsoleFilterProviderEx {

    override fun getDefaultFilters(project: Project): Array<Filter> {
        return  getDefaultFilters(project, GlobalSearchScope.allScope(project))
    }

    override fun getDefaultFilters(project: Project, scope: GlobalSearchScope): Array<Filter> {
        return arrayOf(DmdConsoleFilter(project, scope))
    }
}

class DmdConsoleFilter(val project: Project, val scope: GlobalSearchScope) : Filter {
    override fun applyFilter(line: String, entireLength: Int): Filter.Result? {
        val messageSuffix = pattern.find(line) ?: return null
        val pathEnd = messageSuffix.groups[1]!!.range.last + 1
        val path = line.substring(0, pathEnd)
        val lineNumber = Integer.parseInt(messageSuffix.groupValues[2]) - 1

        val offset = entireLength - line.length
        val file = LocalFileSystem.getInstance().findFileByPathIfCached(path)?: return null
        return Filter.Result(offset, offset + messageSuffix.range.last, OpenFileHyperlinkInfo(project, file, lineNumber, 0))
    }

    companion object {
        val pattern = Regex("""(\.di?)\((\d+)\):""")
    }
}
