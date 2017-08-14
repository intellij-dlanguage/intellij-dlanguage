package net.masterthought.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiElementVisitor
import net.masterthought.dlanguage.DLanguageBundle
import net.masterthought.dlanguage.psi.DLanguageVisitor
import net.masterthought.dlanguage.psi.impl.named.DLanguageIdentifierImpl
import net.masterthought.dlanguage.resolve.DResolveUtil
import net.masterthought.dlanguage.utils.Identifier


/**
 * Created by francis on 7/25/2017.
 * due to string mixins it is very impractical to determine whether something is actually an undefined symbol
 */

fun symbolIsDefinedByDefault(identifier: Identifier): Boolean {
    val name = identifier.name
    if (name == "length" || name == "Exception" || name == "Throwable" || name == "popFront" || name == "Object" || name == "dup" || name == "ptr" || name == "TypeInfo" || name == "idup")//todo remove these when the runtime is added by default
        return true
    return name == "sizeof" || name == "nan" || name == "init" || name == "mangleof" || name == "stringof" || name == "alignof" || name == "max" || name == "min" || name == "inifinity" || name == "dig" || name == "epsilon" || name == "mant_dig" || name == "max_10_exp" || name == "max_exp" || name == "min_10_exp" || name == "min_exp" || name == "min_normal" || name == "re" || name == "im" || name == "classinfo"
}

class PossiblyUndefinedSymbol : LocalInspectionTool() {
    class UndefinedSymbolVisitor(val holder: ProblemsHolder) : DLanguageVisitor() {

        val log: Logger = Logger.getInstance(this::class.java)
        override fun visitIdentifier(identifier: DLanguageIdentifierImpl?) {
            if (identifier != null) {
                val start = System.currentTimeMillis()
                if (DResolveUtil.getInstance(identifier.project).shouldNotResolveToAnything(identifier)) {
                    return
                }
//                if(SpecialCaseResolve.isApplicable(identifier)){
//                    if(SpecialCaseResolve.findDefinitionNode(identifier).isEmpty() && !symbolIsDefinedByDefault(identifier)){
//                        holder.registerProblem(identifier, "Possibly undefined symbol")
//                    }
//                }
//                else if (BasicResolve.findDefinitionNode(identifier.project, identifier).isEmpty() && !symbolIsDefinedByDefault(identifier)) {
//                    holder.registerProblem(identifier, "Possibly undefined symbol")
//                }
                if (DResolveUtil.getInstance(identifier.project).findDefinitionNode(identifier, true).isEmpty() && !symbolIsDefinedByDefault(identifier)) {
                    holder.registerProblem(identifier, "Possibly undefined symbol")//todo add quick fix
                }
                val end = System.currentTimeMillis()
                if (end - start > 50) {
                    log.info("resolve took a while" + (end - start))
                    DResolveUtil.getInstance(identifier.project).findDefinitionNode(identifier, true)
                }
//                log.info("time to resolve in inspection:" + (end - start))
            }
        }
    }


    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = UndefinedSymbolVisitor(holder)

    override fun getDisplayName(): String = DLanguageBundle.message("d.inspections.symbol.possiblyundefined.displayname")

    override fun getGroupDisplayName(): String = DLanguageBundle.message("d.inspections.symbol.possiblyundefined.groupname")
}
