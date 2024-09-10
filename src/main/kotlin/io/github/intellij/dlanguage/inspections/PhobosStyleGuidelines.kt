package io.github.intellij.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.psi.DlangVisitor
import io.github.intellij.dlanguage.psi.named.*
import java.util.regex.Pattern

/**
 * Created by francis on 1/5/2018.
 */
class PhobosStyleGuidelines : LocalInspectionTool() {
    override fun getDescriptionFileName(): String = "PhobosStyleGuidelines.html"
    override fun getDisplayName(): String = "Name does not comply with Phobos Style Guidelines"
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): DlangVisitor = PhobosStyleGuidelinesVisitor(holder)
    override fun getGroupDisplayName(): String = DlangBundle.message("d.inspections.groupname")
}

class PhobosStyleGuidelinesVisitor(val holder: ProblemsHolder) : DlangVisitor() {
    val varFunNameRegex = Pattern.compile("^([\\p{Ll}_][_\\w\\d]*|[\\p{Lu}\\d_]+)$")
    val aggregateNameRegex = Pattern.compile("^\\p{Lu}[\\w\\d]*$")
    val moduleNameRegex = Pattern.compile("^[\\p{Ll}_\\d]+$")

    fun checkName(type: String, name: String, elem: PsiElement, regex: Pattern) {
        val matcher = regex.matcher(name)
        if (name.length > 0 && matcher.find())
            holder.registerProblem(elem, type + " name '" + name + "' does not match style guidelines.")
    }

    override fun visitModuleDeclaration(o: DLanguageModuleDeclaration) {
        var identifierChain = o.identifierChain
        while (identifierChain != null) {
            checkName("Module", identifierChain.identifier!!.text, identifierChain.identifier!!, moduleNameRegex)
            identifierChain = identifierChain.identifierChain
        }
    }

    override fun visitDeclarator(o: DLanguageIdentifierInitializer) {
        o.name?:return
        checkName("Variable", StringUtil.decapitalize(o.name!!), o.nameIdentifier!!, varFunNameRegex)
    }

    override fun visitFunctionDeclaration(o: DLanguageFunctionDeclaration) {
        o.name?:return
        checkName("Function", StringUtil.decapitalize(o.name!!), o.nameIdentifier!!, varFunNameRegex)
    }

    override fun visitClassDeclaration(o: DLanguageClassDeclaration) {
        o.name?:return
        checkName("Class", o.name!!, o, aggregateNameRegex)
    }

    override fun visitInterfaceDeclaration(o: DLanguageInterfaceDeclaration) {
        o.name?:return
        checkName("Interface", o.name!!, o, aggregateNameRegex)
    }

    override fun visitStructDeclaration(o: DLanguageStructDeclaration) {
        o.name?:return
        checkName("Struct", o.name!!, o, aggregateNameRegex)
    }

    override fun visitUnionDeclaration(o: DLanguageUnionDeclaration) {
        o.name?:return
        checkName("Union", o.name!!, o, aggregateNameRegex)
    }

    override fun visitEnumDeclaration(o: DLanguageEnumDeclaration) {
        o.name?:return
        checkName("Enum", o.name!!, o, aggregateNameRegex)
    }
}
