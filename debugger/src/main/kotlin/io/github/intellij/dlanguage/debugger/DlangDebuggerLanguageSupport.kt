package io.github.intellij.dlanguage.debugger

import com.intellij.execution.configurations.RunProfile
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.IndexNotReadyException
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.NlsSafe
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xdebugger.XSourcePosition
import com.intellij.xdebugger.evaluation.XDebuggerEditorsProvider
import com.jetbrains.cidr.execution.debugger.*
import com.jetbrains.cidr.execution.debugger.backend.DebuggerDriver
import com.jetbrains.cidr.execution.debugger.backend.LLValue
import com.jetbrains.cidr.execution.debugger.breakpoints.CidrLineBreakpointFileTypesProvider
import com.jetbrains.cidr.execution.debugger.evaluation.CidrDebuggerTypesHelper
import com.jetbrains.cidr.execution.debugger.evaluation.CidrDebuggerTypesHelperBase
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.interfaces.Statement
import io.github.intellij.dlanguage.psi.resolve.processor.GenericProcessor
import io.github.intellij.dlanguage.psi.scope.PsiScopesUtil

class DlangDebuggerLanguageSupport : CidrDebuggerLanguageSupport() {
    override fun getSupportedDebuggerLanguages(): Set<DebuggerDriver.DebuggerLanguage> = setOf(DebuggerDriver.StandardDebuggerLanguage.D)

    override fun createDebuggerTypesHelper(process: CidrDebugProcess): CidrDebuggerTypesHelperBase {
        return DlangDebuggerTypesHelper(process)
    }

    override fun createEvaluator(frame: CidrStackFrame): CidrEvaluator {
        return DlangEvaluator(frame)
    }

    override fun createFrameTypeDecorator(frame: CidrStackFrame): CidrFrameTypeDecorator {
        return super.createFrameTypeDecorator(frame)
    }

    override fun createEditor(project: Project, profile: RunProfile?): XDebuggerEditorsProvider? {
        // TODO restrict only to Dlang profiles only
        //if (profile !is DlangRunDubConfiguration)
        return CidrDebuggerEditorsProvider()
    }
}

class DlangDebuggerEditorsExtension : CidrDebuggerEditorsExtensionBase()

class DlangLineBreakpointFileTypeProvider: CidrLineBreakpointFileTypesProvider {
    override fun getFileTypes(): Set<FileType?> = setOf(DlangFileType)
}

class DlangEvaluator(frame: CidrStackFrame) : CidrEvaluator(frame)

class DlangDebuggerTypesHelper(process: CidrDebugProcess) : CidrDebuggerTypesHelper(process) {
    override fun isImplicitContextVariable(position: XSourcePosition, `var`: LLValue): Boolean =
        `var`.name == "this" || `var`.name == "super"

    override fun resolveToDeclaration(position: XSourcePosition?, `var`: LLValue): PsiElement? {
        val context = this.getContextElement(position) ?: return null
        val ref: PsiReference = this.createReferenceFromText(`var`, context)
        return try {
            ref.resolve()
        } catch (_: IndexNotReadyException) {
            null
        }
    }

    override fun getContextElement(position: XSourcePosition?): PsiElement? {
        val element = super.getContextElement(position)
        // We want to return the statement containing the element
        val parentContext = PsiTreeUtil.getParentOfType(element, Statement::class.java)
        return parentContext
    }

    override fun createReferenceFromText(`var`: LLValue, context: PsiElement): PsiReference {
        return DebuggerLocalReference(context,  `var`.name)
    }
}

class DebuggerLocalReference(private val element: PsiElement, private val name: String) : PsiReference {
    override fun getElement(): PsiElement {
        return element
    }

    override fun getRangeInElement(): TextRange {
        return element.textRange
    }

    override fun resolve(): PsiElement? {
        val processor = GenericProcessor(name, element)
        // TODO need to add support for variable arguments (example: args in `void writeln(T...)(T args)`)
        val parentStatement = PsiTreeUtil.getParentOfType(element, Statement::class.java) ?: return null
        PsiScopesUtil.treeWalkUp(processor, parentStatement, null)
        if (processor.getResult().size == 1) {
            return processor.getResult().first().element as DNamedElement
        }
        return null
    }

    override fun getCanonicalText(): @NlsSafe String = name

    override fun handleElementRename(p0: String): PsiElement? = null

    override fun bindToElement(p0: PsiElement): PsiElement? = null

    override fun isReferenceTo(p0: PsiElement): Boolean = false

    override fun isSoft(): Boolean = true
}
