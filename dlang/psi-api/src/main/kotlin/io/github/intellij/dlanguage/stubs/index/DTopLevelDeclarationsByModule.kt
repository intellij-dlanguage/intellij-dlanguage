package io.github.intellij.dlanguage.stubs.index

import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.*
import io.github.intellij.dlanguage.psi.DlangPsiFile
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.interfaces.HasMembers
import io.github.intellij.dlanguage.psi.named.DLanguageSingleImport
import io.github.intellij.dlanguage.stubs.index.DTopLevelDeclarationIndex.Companion.getTopLevelSymbols

/**
 * Created by francis on 6/17/2017.
 */
class DTopLevelDeclarationsByModule : StringStubIndexExtension<DNamedElement>() {
    override fun getKey(): StubIndexKey<String, DNamedElement> {
        return KEY
    }

    override fun getVersion(): Int {
        return VERSION
    }

    companion object {
        val KEY: StubIndexKey<String, DNamedElement> = StubIndexKey.createIndexKey("d.globally.accessible.module")
        val VERSION = 5
        fun <S : NamedStubBase<T>, T : DNamedElement> indexTopLevelDeclarationsByModule(stub: S, sink: IndexSink) {
            if (topLevelDeclaration(stub)) {
                val fileName = (stub.psi.containingFile as DlangPsiFile).getFullyQualifiedModuleName()
                sink.occurrence(KEY, fileName)
            }
        }

        //todo better name/stop repeating type signature?
        fun getSymbolsFromImport(import: DLanguageSingleImport): MutableSet<DNamedElement> {
            if (import.applicableImportBinds.size == 0) {
                return StubIndex.getElements(KEY, import.importedModuleName, import.project, GlobalSearchScope.allScope(import.project), DNamedElement::class.java).toMutableSet()
            }
            val symbols = mutableSetOf<DNamedElement>()
            for (bind in import.applicableImportBinds) {
                for (resolvedBind in getTopLevelSymbols(bind, import.importedModuleName, import.project)) {
                    symbols.add(resolvedBind)
                    if (resolvedBind is HasMembers<*>) {
                        symbols.addAll(resolvedBind.members.map { it.psi as DNamedElement })
                    }
                }
            }
            return symbols
        }
    }
}
