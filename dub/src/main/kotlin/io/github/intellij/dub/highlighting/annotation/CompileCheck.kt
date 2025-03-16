package io.github.intellij.dub.highlighting.annotation

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiFile
import com.intellij.util.containers.ContainerUtil
import io.github.intellij.dlanguage.highlighting.annotation.DProblem
import io.github.intellij.dlanguage.highlighting.annotation.external.DlangLinter
import io.github.intellij.dlanguage.highlighting.annotation.external.LinterHelper
import io.github.intellij.dub.service.DubBinaryPathProvider
import io.github.intellij.dub.tools.DubProcessListener
import java.util.regex.Pattern

/**
 * Uses dub (with the default compiler) to check for compile errors periodically
 */
class CompileCheck : DlangLinter {
    override fun checkFileSyntax(file: PsiFile): Array<DProblem> {
        val dubPath: String? = DubBinaryPathProvider.getDubPath()
        if (!DubBinaryPathProvider.isDubAvailable()) return arrayOf()
        val result = processFile(file, dubPath!!)
        return if (StringUtil.isNotEmpty(result)) {
            findProblems(result, file).toTypedArray()
        } else arrayOf()
    }

    private fun processFile(file: PsiFile, dubPath: String): String {
        val cmd: GeneralCommandLine = GeneralCommandLine()
            .withWorkDirectory(file.getProject().getBasePath())
            .withExePath(dubPath)
            .withEnvironment("DFLAGS", "-o-")
            .withParameters("build", "--combined", "-q")
        try {
            val listener = DubProcessListener()
            val process = OSProcessHandler(cmd.createProcess(), cmd.getCommandLineString())
            process.addProcessListener(listener)
            process.startNotify()
            process.waitFor()
            return listener.getOutput()
        } catch (e: ExecutionException) {
            LOG.warn("There was a problem running 'dub build --combined -q'", e)
        }
        return ""
    }

    private fun findProblems(stdout: String, file: PsiFile): List<DProblem> {
        val lints = StringUtil.split(stdout, "\n")
        val problems: MutableList<DProblem> = ArrayList()
        for (lint in lints) {
            ContainerUtil.addIfNotNull<CompilerProblem?>(problems, parseProblem(lint, file))
        }
        return problems
    }

    private fun parseProblem(lint: String, file: PsiFile): CompilerProblem? {
        LOG.debug(lint)

        // Example DUB error:
        // src/hello.d(3,1): Error: only one main allowed
        val p = Pattern.compile("([\\w\\\\/]+\\.d)\\((\\d+),(\\d+)\\):\\s(\\w+):(.+)")
        val m = p.matcher(lint)
        var sourceFile = ""
        var message = ""
        var line = 0
        var column = 0
        var severity = ""
        var hasMatch = false
        while (m.find()) {
            hasMatch = true
            sourceFile = m.group(1)
            line = m.group(2).toInt()
            column = m.group(3).toInt()
            severity = m.group(4)
            message = m.group(5)
        }
        if (hasMatch && isSameFile(file, sourceFile)) {
            val range: TextRange? = LinterHelper.calculateTextRange(file, line, column)
            if (range != null) {
                return CompilerProblem(range, message, severity)
            }
        }
        return null
    }

    private fun isSameFile(file: PsiFile, relativeOtherFilePath: String): Boolean {
        val filePath: String = file.getVirtualFile().getPath()
        val unixRelativeOtherFilePath = relativeOtherFilePath.replace('\\', '/')
        return filePath.endsWith(unixRelativeOtherFilePath)
    }

    class CompilerProblem(range: TextRange, message: String, severity: String) : DProblem {
        val severity: String
        val range: TextRange
        val message: String

        init {
            this.range = range
            this.severity = severity
            this.message = message
        }

        override fun createAnnotations(file: PsiFile, holder: AnnotationHolder) {
            if (severity == "Error") {
                holder.newAnnotation(HighlightSeverity.ERROR, message).range(range).create()
            } else {
                holder.newAnnotation(HighlightSeverity.WARNING, message).range(range).create()
            }
        }
    }

    companion object {
        private val LOG = Logger.getInstance(
            CompileCheck::class.java
        )
    }
}
