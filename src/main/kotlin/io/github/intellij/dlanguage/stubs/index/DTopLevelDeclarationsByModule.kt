package io.github.intellij.dlanguage.stubs.index

import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.*
import io.github.intellij.dlanguage.psi.DlangFile
import io.github.intellij.dlanguage.psi.DlangSingleImport
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.interfaces.HasMembers
import io.github.intellij.dlanguage.stubs.DlangIdentifierStub
import io.github.intellij.dlanguage.stubs.index.DTopLevelDeclarationIndex.Companion.getTopLevelSymbols

/**
 * Created by francis on 6/17/2017.
 */
class DTopLevelDeclarationsByModule : StringStubIndexExtension<io.github.intellij.dlanguage.psi.interfaces.DNamedElement>() {
    override fun getKey(): StubIndexKey<String, io.github.intellij.dlanguage.psi.interfaces.DNamedElement> {
        return KEY
    }

    override fun getVersion(): Int {
        return VERSION
    }

    companion object {
        val KEY: StubIndexKey<String, io.github.intellij.dlanguage.psi.interfaces.DNamedElement> = StubIndexKey.createIndexKey<String, io.github.intellij.dlanguage.psi.interfaces.DNamedElement>("d.globally.accessible.module")
        val VERSION = 2
        fun <S : NamedStubBase<T>, T : io.github.intellij.dlanguage.psi.interfaces.DNamedElement> indexTopLevelDeclarationsByModule(stub: S, sink: IndexSink) {
            if (stub !is io.github.intellij.dlanguage.stubs.DlangIdentifierStub && topLevelDeclaration(stub)) {
                val fileName = (stub.psi.containingFile as io.github.intellij.dlanguage.psi.DlangFile).moduleOrFileName
                sink.occurrence(DTopLevelDeclarationsByModule.KEY, fileName)
            }
        }

        //todo better name/stop repeating type signature?
        fun getSymbolsFromImport(import: io.github.intellij.dlanguage.psi.DlangSingleImport): MutableSet<io.github.intellij.dlanguage.psi.interfaces.DNamedElement> {
            if (import.applicableImportBinds.size == 0) {
                return StubIndex.getElements(KEY, import.importedModuleName, import.project, GlobalSearchScope.everythingScope(import.project), io.github.intellij.dlanguage.psi.interfaces.DNamedElement::class.java).toMutableSet()
            }
            val symbols = mutableSetOf<io.github.intellij.dlanguage.psi.interfaces.DNamedElement>()
            for (bind in import.applicableImportBinds) {
                for (resolvedBind in getTopLevelSymbols(bind, import.importedModuleName, import.project)) {
                    symbols.add(resolvedBind)
                    if (resolvedBind is io.github.intellij.dlanguage.psi.interfaces.HasMembers<*>) {
                        symbols.addAll(resolvedBind.members.map { it.psi as io.github.intellij.dlanguage.psi.interfaces.DNamedElement })
                    }
                }
            }
            return symbols
        }
    }
}
