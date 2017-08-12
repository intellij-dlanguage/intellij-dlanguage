package net.masterthought.dlanguage.stubs.index

import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.*
import net.masterthought.dlanguage.psi.DLanguageFile
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.psi.interfaces.HasMembers
import net.masterthought.dlanguage.stubs.DLanguageIdentifierStub
import net.masterthought.dlanguage.stubs.DLanguageSingleImportStub
import net.masterthought.dlanguage.stubs.index.DTopLevelDeclarationIndex.Companion.getTopLevelSymbols

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
        val KEY: StubIndexKey<String, DNamedElement> = StubIndexKey.createIndexKey<String, DNamedElement>("d.globally.accessible.module")
        val VERSION = 2
        fun <S : NamedStubBase<T>, T : DNamedElement> indexTopLevelDeclarationsByModule(stub: S, sink: IndexSink) {
            if (stub !is DLanguageIdentifierStub && topLevelDeclaration(stub)) {
                val fileName = (stub.psi.containingFile as DLanguageFile).moduleOrFileName
                sink.occurrence(DTopLevelDeclarationsByModule.KEY, fileName)
            }
        }

        //todo better name/stop repeating type signature?
        fun getSymbolsFromImport(import: DLanguageSingleImportStub): MutableSet<NamedStub<*>> {
            if (import.numBinds() == 0) {
                return StubIndex.getElements(KEY, import.importedModule, import.project, GlobalSearchScope.everythingScope(import.project), DNamedElement::class.java).map { (it as StubBasedPsiElementBase<*>).stub as NamedStub<*> }.toMutableSet()//this is yuck todo
            }
            val symbols = mutableSetOf<NamedStub<*>>()
            for (bind in import.binds) {
                for (resolvedBind in getTopLevelSymbols(bind, import.importedModule, import.project)) {
                    symbols.add((resolvedBind as StubBasedPsiElementBase<*>).stub as NamedStub<*>)//this is also yuck todo
                    if (resolvedBind is HasMembers<*>) {
                        symbols.addAll(resolvedBind.members)
                    }
                }
            }
            return symbols
        }
    }
}
