package net.masterthought.dlanguage.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiElementVisitor
import net.masterthought.dlanguage.psi.DLanguageVisitor
import net.masterthought.dlanguage.psi.impl.named.DLanguageIdentifierImpl
import net.masterthought.dlanguage.resolve.DResolveUtil.shouldNotResolveToAnything
import net.masterthought.dlanguage.resolve.processors.basic.BasicResolve
import net.masterthought.dlanguage.utils.Identifier


/**
 * Created by francis on 7/25/2017.
 * due to string mixins it is very impractical to determine whether something is actually an undefined symbol
 */

fun symbolIsDefinedByDefault(identifier: Identifier): Boolean {
    val name = identifier.name
    if (name == "length" || name == "Exception" || name == "Throwable" || name == "popFront" || name == "Object" || name == "dup")//todo remove these when the runtime is added by defualt
        return true
    return name == "sizeof" || name == "nan" || name == "init" || name == "mangleof" || name == "stringof" || name == "alignof" || name == "max" || name == "min" || name == "inifinity" || name == "dig" || name == "epsilon" || name == "mant_dig" || name == "max_10_exp" || name == "max_exp" || name == "min_10_exp" || name == "min_exp" || name == "min_normal" || name == "re" || name == "im" || name == "classinfo"
}

class PossiblyUndefinedSymbol : LocalInspectionTool() {
    class UndefinedSymbolVisitor(val holder: ProblemsHolder) : DLanguageVisitor() {

        val log: Logger = Logger.getInstance(this::class.java)
        override fun visitIdentifier(identifier: DLanguageIdentifierImpl?) {
            if (identifier != null) {
//                val start = System.currentTimeMillis()
                if (shouldNotResolveToAnything(identifier)) {
                    return
                }
                if (BasicResolve.findDefinitionNode(identifier.project, identifier).isEmpty() && !symbolIsDefinedByDefault(identifier)) {
                    holder.registerProblem(identifier, "Possibly undefined symbol")
                }
//                val end = System.currentTimeMillis()
//                log.info("time to resolve in inspection:" + (end-start))
//                if ((identifier.reference as DReference).multiResolve(false).isEmpty() && !symbolIsDefinedByDefault(identifier)) {
//                    holder.registerProblem(identifier, "Possibly undefined symbol")//todo add quick fix, and use stubs
//                }
            }
        }
    }


    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return UndefinedSymbolVisitor(holder)
    }

    override fun getDisplayName(): String {
        return "Possibly Undefined Symbol"
    }

    override fun getGroupDisplayName(): String {
        return "Symbols"
    }
}
